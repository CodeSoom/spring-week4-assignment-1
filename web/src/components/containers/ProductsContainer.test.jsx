import React from 'react';

import { useDispatch, useSelector } from 'react-redux';
import { getDefaultMiddleware } from '@reduxjs/toolkit';

import { render, fireEvent, waitFor } from '@testing-library/react';
import configureStore from 'redux-mock-store';

import ProductsContainer from './ProductsContainer';

import { selectProduct, setProducts } from '../../redux/slice';

import { productsFixture } from '../../fixtures/products';

const mockStore = configureStore(getDefaultMiddleware());

jest.mock('react-redux');
jest.mock('../../api/products');

describe('ProductsContainer', () => {
  let store;

  const renderProductsContainer = () => render((
    <ProductsContainer />
  ));

  beforeEach(() => {
    given('products', () => productsFixture);
  });

  beforeEach(() => {
    store = mockStore(() => ({
      products: given.products,
    }));
    useSelector.mockImplementation((selector) => selector(store.getState()));
    useDispatch.mockImplementation(() => store.dispatch);
  });

  context('when products is exist', () => {
    beforeEach(() => {
      given('products', () => productsFixture);
    });

    it('renders products', () => {
      const { container } = renderProductsContainer();

      expect(container).toHaveTextContent(productsFixture[0].name);
      expect(container).toHaveTextContent(productsFixture[0].maker);
      expect(container).toHaveTextContent(`${productsFixture[0].price}원`);
    });
  });

  context('when products is empty', () => {
    beforeEach(() => {
      given('products', () => []);
    });

    it('renders products', () => {
      const { container } = renderProductsContainer();

      expect(container)
        .toHaveTextContent('등록된 상품이 없습니다. 상품을 등록해 주세요.');
    });
  });

  describe('Selecting product', () => {
    it('runs selectProduct', () => {
      const { getByText } = renderProductsContainer();

      fireEvent.click(getByText('상세'));

      const actions = store.getActions();

      expect(actions[0]).toEqual(selectProduct(productsFixture[0].id));
    });
  });

  describe('Clicking delete button', () => {
    it('runs setProducts', async () => {
      const { getByText } = renderProductsContainer();

      fireEvent.click(getByText('삭제'));

      await waitFor(() => {
        const actions = store.getActions();

        expect(setProducts.match(actions[0])).toBe(true);
      });
    });
  });
});
