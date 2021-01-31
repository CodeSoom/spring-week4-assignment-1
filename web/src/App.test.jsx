import { MemoryRouter } from 'react-router-dom';

import { useDispatch, useSelector } from 'react-redux';
import { getDefaultMiddleware } from '@reduxjs/toolkit';

import { render } from '@testing-library/react';
import configureStore from 'redux-mock-store';

import App from './App';

import { productsFixture } from './fixtures/products';

const mockStore = configureStore(getDefaultMiddleware());

jest.mock('react-redux');
jest.mock('./api/products');

describe('App', () => {
  const renderApp = (path = '/') => render((
    <MemoryRouter initialEntries={[path]}>
      <App />
    </MemoryRouter>
  ));

  beforeEach(() => {
    const store = mockStore(() => ({
      product: {},
      products: productsFixture,
      form: {
        name: '',
        maker: '',
        price: '',
      },
    }));
    useSelector.mockImplementation((selector) => selector(store.getState()));
    useDispatch.mockImplementation(() => store.dispatch);
  });

  context('with /products', () => {
    it('renders products page', () => {
      const { container } = renderApp('/products');

      expect(container).toHaveTextContent('고양이 장난감 목록');
    });
  });

  context('with /products/product', () => {
    it('renders product register page', () => {
      const { container } = renderApp('/products/product');

      expect(container).toHaveTextContent('고양이 장난감 등록');
    });
  });

  context('with /products/{id}', () => {
    it('renders product detail page', () => {
      const { container } = renderApp('/products/1');

      expect(container).toHaveTextContent('고양이 장난감 상세');
    });
  });

  context('with undefined path', () => {
    it('renders products page', () => {
      const { container } = renderApp('/ABC');

      expect(container).toHaveTextContent('고양이 장난감 목록');
    });
  });
});
