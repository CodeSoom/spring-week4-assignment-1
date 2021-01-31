const { deleteAll, create } = require('./api');
const product = require('./fixtures/product');

Feature('Products');

Before(async () => {
  await deleteAll();
});

Scenario('등록된 장난감이 없는 경우 빈 메세지를 볼 수 있다.', ({ I }) => {
  I.amOnPage('/products');

  I.see('등록된 상품이 없습니다. 상품을 등록해 주세요.');
});

Scenario('장난감을 등록하면 장난감 목록을 조회할 수 있다.', ({ I }) => {
  I.amOnPage('/products/product');

  I.fillField('이름', product.name);
  I.fillField('메이커', product.maker);
  I.fillField('가격', product.price);

  I.click('등록');

  I.see(product.name);
  I.see(product.maker);
  I.see(product.price);
});

Scenario('등록하기 버튼을 눌러서 등록페이지로 이동할 수 있다.', ({ I }) => {
  I.amOnPage('/products');

  I.click('등록하기');

  I.see('고양이 장난감 등록');
});

Scenario('장난감 상세를 누르면 상세페이지로 이동합니다.', async ({ I }) => {
  await create(product);

  I.amOnPage('/products');

  I.click('상세');

  I.see(product.name);
});

Scenario('장난감을 삭제하면 목록에서 사라진다.', async ({ I }) => {
  await create(product);

  I.amOnPage('/products');

  I.click('삭제');

  I.see('등록된 상품이 없습니다. 상품을 등록해 주세요.');
});
