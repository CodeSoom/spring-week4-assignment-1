import { getDefaultMiddleware } from '@reduxjs/toolkit';

import configureStore from 'redux-mock-store';
import { productsFixture } from '../fixtures/products';

import {
  setProducts, loadProducts, registerProduct, success, clearForm, setProduct,
  loadProduct, removeProduct, setMode, updateProduct,
} from './slice';

const mockStore = configureStore(getDefaultMiddleware());

jest.mock('../api/products');

describe('slice', () => {
  let store;

  beforeEach(() => {
    store = mockStore(() => ({
      product: productsFixture[0],
    }));
  });

  describe('loadProducts', () => {
    it('runs setProducts', async () => {
      await store.dispatch(loadProducts());

      const actions = store.getActions();

      expect(setProducts.match(actions[0])).toBe(true);
    });
  });

  describe('loadProduct', () => {
    const id = 1;

    it('runs setProduct', async () => {
      await store.dispatch(loadProduct(id));

      const actions = store.getActions();

      expect(setProduct.match(actions[0])).toBe(true);
    });
  });

  describe('registerProducts', () => {
    it('runs success', async () => {
      await store.dispatch(registerProduct());

      const actions = store.getActions();

      expect(clearForm.match(actions[0])).toBe(true);
      expect(success.match(actions[1])).toBe(true);
    });
  });

  describe('deleteProduct', () => {
    const id = 1;

    it('runs setProducts', async () => {
      await store.dispatch(removeProduct(id));

      const actions = store.getActions();

      expect(setProducts.match(actions[0])).toBe(true);
    });
  });

  describe('updateProduct', () => {
    it('runs setProduct', async () => {
      await store.dispatch(updateProduct());

      const actions = store.getActions();

      expect(setProduct.match(actions[0])).toBe(true);
      expect(setMode.match(actions[1])).toBe(true);
    });
  });
});
