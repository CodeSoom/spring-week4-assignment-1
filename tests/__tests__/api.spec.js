import frisby from 'frisby';

frisby.baseUrl('http://localhost:8080');

const { Joi } = frisby;

const product = Joi.object({
  id: Joi.number(),
  name: Joi.string(),
  maker: Joi.string(),
  price: Joi.number(),
  imageUrl: Joi.string().optional().allow(null),
});

describe('Products', () => {
  const toy = {
    name: '뱀 장난감',
    maker: '애용이네 장난감',
    price: 10000,
    imageUrl: 'http://localhost:8080/snake',
  };

  describe('GET /products', () => {
    beforeEach(async () => {
      await frisby.post('/products', toy);
    });

    it('responses products', async () => {
      await frisby.get('/products')
        .expect('status', 200)
        .expect('jsonTypes', Joi.array().items(product.required()));
    });
  });

  describe('GET /products/{id}', () => {
    let id;

    context('with existing product', () => {
      beforeEach(async () => {
        const { json } = await frisby.post('/products', toy);
        id = json.id;
      });

      it('response product', async () => {
        const { json } = await frisby.get(`/products/${id}`)
          .expect('status', 200);

        expect(json).toMatchObject(toy);
      });
    });

    context('with not existing product', () => {
      beforeEach(() => {
        id = 9999;
      });

      it('response not found', async () => {
        await frisby.get(`/products/${id}`)
          .expect('status', 404);
      });
    });
  });

  describe('POST /products', () => {
    context('with correct product', () => {
      it('responses created product', async () => {
        await frisby.post('/products', toy)
          .expect('status', 201)
          .expect('jsonTypes', product);
      });
    });
  });

  describe('PATCH /products/{id}', () => {
    let id;

    context('with correct product', () => {
      const body = {
        name: '물고기 장난감',
        maker: '멍멍이네 장난감가게',
        price: 50000,
        imageUrl: 'http://localhost:8080/fish',
      };

      beforeEach(async () => {
        const { json } = await frisby.post('/products', toy);
        id = json.id;
      });

      it('responses updated product', async () => {
        const { json } = await frisby.patch(`/products/${id}`, body)
          .expect('status', 200);

        expect(json).toMatchObject(body);
      });
    });

    context('with not existing product', () => {
      beforeEach(() => {
        id = 99999;
      });

      it('responses bad request', async () => {
        await frisby.patch(`/products/${id}`, toy)
          .expect('status', 404);
      });
    });
  });

  describe('DELETE /products/{id}', () => {
    let id;

    context('with existing product', () => {
      beforeEach(async () => {
        const { json } = await frisby.post('/products', toy);
        id = json.id;
      });

      it('responses No Content', async () => {
        await frisby.del(`/products/${id}`)
          .expect('status', 204);
      });
    });

    context('with not existing product', () => {
      beforeEach(() => {
        id = 99999;
      });

      it('responses Not Found', async () => {
        await frisby.del(`/products/${id}`)
          .expect('status', 404);
      });
    });
  });
});
