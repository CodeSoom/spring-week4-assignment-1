import { createSlice } from '@reduxjs/toolkit';

import {
  fetchProducts, fetchProduct, postProduct, deleteProduct, putProduct,
} from '../api/products';

const initialState = {
  product: {},
  products: [],
  selectedProductId: null,
  form: {
    name: '',
    maker: '',
    price: '',
    imageUrl: '',
  },
  status: null,
  mode: 'READ',
};

const { actions, reducer } = createSlice({
  name: 'app',
  initialState,
  reducers: {
    setProducts: (state, { payload: products }) => ({
      ...state,
      products,
    }),
    updateForm: (state, { payload: { name, value } }) => ({
      ...state,
      form: {
        ...state.form,
        [name]: value,
      },
    }),
    setProduct: (state, { payload: product }) => ({
      ...state,
      product,
    }),
    initializeStatus: (state) => ({
      ...state,
      status: initialState.status,
    }),
    success: (state, { payload: message }) => ({
      ...state,
      status: {
        type: 'SUCCESS',
        message,
      },
    }),
    clearForm: (state) => ({
      ...state,
      form: initialState.form,
    }),
    clearSelectedProductId: (state) => ({
      ...state,
      selectedProductId: initialState.selectedProductId,
    }),
    selectProduct: (state, { payload: id }) => ({
      ...state,
      selectedProductId: id,
    }),
    setMode: (state, { payload: mode }) => ({
      ...state,
      form: {
        name: state.product.name,
        maker: state.product.maker,
        price: state.product.price,
        imageUrl: state.product.imageUrl,
      },
      mode,
    }),
  },
});

export const {
  setProducts,
  updateForm,
  success,
  initializeStatus,
  clearForm,
  selectProduct,
  clearSelectedProductId,
  setProduct,
  setMode,
} = actions;

export const loadProducts = () => async (dispatch) => {
  const products = await fetchProducts();
  dispatch(setProducts(products));
};

export const registerProduct = () => async (dispatch, getState) => {
  const { form } = getState();

  await postProduct(form);

  dispatch(clearForm());
  dispatch(success('상품이 등록되었습니다.'));
};

export const loadProduct = (id) => async (dispatch) => {
  const product = await fetchProduct(id);

  dispatch(setProduct(product));
};

export const removeProduct = (id) => async (dispatch) => {
  await deleteProduct(id);
  dispatch(loadProducts());
};

export const updateProduct = () => async (dispatch, getState) => {
  const { product, form } = getState();

  const updatedProduct = await putProduct({
    ...product,
    ...form,
  });

  dispatch(setProduct(updatedProduct));
  dispatch(setMode('READ'));
};

export default reducer;
