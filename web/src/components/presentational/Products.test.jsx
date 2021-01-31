import React from 'react';
import { fireEvent, render } from '@testing-library/react';

import Products from './Products';

import { productsFixture } from '../../fixtures/products';

describe('Products', () => {
  const handleClick = jest.fn();
  const handleClickDelete = jest.fn();

  const renderProducts = () => render((
    <Products
      products={given.products}
      onClick={handleClick}
      onClickDelete={handleClickDelete}
    />
  ));

  beforeEach(() => {
    jest.clearAllMocks();
  });

  beforeEach(() => {
    given('products', () => productsFixture);
  });

  context('when products is exist', () => {
    it('renders products', () => {
      const { container } = renderProducts();

      expect(container).toHaveTextContent(productsFixture[0].name);
      expect(container).toHaveTextContent(productsFixture[0].maker);
      expect(container).toHaveTextContent(`${productsFixture[0].price}원`);
    });
  });

  context('when products is empty', () => {
    beforeEach(() => {
      given('products', () => []);
    });

    it('renders empty message', () => {
      const { container } = renderProducts();

      expect(container)
        .toHaveTextContent('등록된 상품이 없습니다. 상품을 등록해 주세요.');
    });
  });

  describe('Clicking detail button', () => {
    it('calls onClick handler', () => {
      const { getByText } = renderProducts();

      fireEvent.click(getByText('상세'));

      expect(handleClick).toBeCalledWith(productsFixture[0].id);
    });
  });

  describe('Clicking delete button', () => {
    it('calls onClickDelete handler', () => {
      const { getByText } = renderProducts();

      fireEvent.click(getByText('삭제'));

      expect(handleClickDelete).toBeCalledWith(productsFixture[0].id);
    });
  });
});
