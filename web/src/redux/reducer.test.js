import reducer, {
  setProducts, updateForm, success, clearForm, initializeStatus, selectProduct,
  clearSelectedProductId, setProduct, setMode,
} from './slice';

import { productsFixture } from '../fixtures/products';

describe('reducer', () => {
  const initialState = {
    selectedProductId: null,
    product: {},
    products: [],
    form: {
      name: '',
      maker: '',
      price: '',
      imageUrl: '',
    },
    mode: 'READ',
  };

  describe('setProducts', () => {
    it('updates products', () => {
      const state = reducer(initialState, setProducts(productsFixture));

      expect(state.products).toEqual(productsFixture);
    });
  });

  describe('updateForm', () => {
    const value = '장난감 뱀';

    it('updates form', () => {
      const state = reducer(initialState, updateForm({
        name: 'name',
        value,
      }));

      expect(state.form.name).toBe(value);
    });
  });

  describe('setProduct', () => {
    it('updates product', () => {
      const state = reducer(initialState, setProduct(productsFixture[0]));

      expect(state.product).toEqual(productsFixture[0]);
    });
  });

  describe('success', () => {
    it('updates status', () => {
      const state = reducer(initialState, success('성공'));

      expect(state.status).toEqual({
        type: 'SUCCESS',
        message: '성공',
      });
    });
  });

  describe('clearForm', () => {
    it('updates to initial form', () => {
      const state = reducer({
        ...initialState,
        form: {
          name: '장난감 뱀',
          maker: '애옹이네 장난감',
          price: 5000,
        },
      }, clearForm());

      expect(state.form).toEqual(initialState.form);
    });
  });

  describe('initilizeStatus', () => {
    it('updates initial status', () => {
      const state = reducer({
        ...initialState,
        status: {
          type: 'SUCCESS',
          message: 'Some message',
        },
      }, initializeStatus());

      expect(state.status).toBeNull();
    });
  });

  describe('selectProduct', () => {
    it('updates selectedProductId', () => {
      const state = reducer(initialState, selectProduct(1));

      expect(state.selectedProductId).toBe(1);
    });
  });

  describe('clearSelectedProductId', () => {
    it('updates selectedProductId to be null', () => {
      const state = reducer({
        ...initialState,
        selectedProductId: 1,
      }, clearSelectedProductId());

      expect(state.selectedProductId).toBeNull();
    });
  });

  describe('setMode', () => {
    const product = productsFixture[0];

    it('updates mode and form', () => {
      const state = reducer({
        ...initialState,
        product,
      }, setMode('UPDATE'));

      expect(state.mode).toBe('UPDATE');
      expect(state.form).toEqual({
        name: product.name,
        maker: product.maker,
        price: product.price,
        imageUrl: product.imageUrl,
      });
    });
  });
});
