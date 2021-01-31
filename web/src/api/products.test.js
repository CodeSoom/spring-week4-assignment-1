import axios from 'axios';

import {
  fetchProducts, fetchProduct, postProduct, deleteProduct, putProduct,
} from './products';

import { productsFixture } from '../fixtures/products';

jest.mock('axios');

describe('API', () => {
  describe('fetchProducts', () => {
    beforeEach(() => {
      axios.get.mockResolvedValue({
        data: productsFixture,
      });
    });

    it('responses products', async () => {
      const products = await fetchProducts();

      expect(products).toEqual(productsFixture);
    });
  });

  describe('fetchProduct', () => {
    const id = 1;

    beforeEach(() => {
      axios.get.mockResolvedValue({
        data: productsFixture[0],
      });
    });

    it('responses product', async () => {
      const product = await fetchProduct(id);

      expect(product).toEqual(productsFixture[0]);
    });
  });

  describe('postProduct', () => {
    beforeEach(() => {
      axios.post.mockResolvedValue({
        data: productsFixture[0],
      });
    });

    it('responses product', async () => {
      const product = await postProduct(productsFixture[0]);

      expect(product).toEqual(productsFixture[0]);
    });
  });

  describe('deleteProduct', () => {
    beforeEach(() => {
      axios.delete.mockResolvedValue({
        data: productsFixture[0],
      });
    });

    it('responses nothing', async () => {
      const result = await deleteProduct(productsFixture[0]);

      expect(result).toBeUndefined();
    });
  });

  describe('putProduct', () => {
    beforeEach(() => {
      axios.patch.mockResolvedValue({
        data: productsFixture[0],
      });
    });

    it('responses updatedProduct', async () => {
      const product = await putProduct(productsFixture[0]);

      expect(product).toEqual(productsFixture[0]);
    });
  });
});
