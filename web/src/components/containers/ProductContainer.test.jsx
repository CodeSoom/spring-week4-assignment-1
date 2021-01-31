import { useDispatch, useSelector } from 'react-redux';
import { getDefaultMiddleware } from '@reduxjs/toolkit';

import { render, fireEvent, waitFor } from '@testing-library/react';
import configureStore from 'redux-mock-store';

import ProductContainer from './ProductContainer';

import { productsFixture } from '../../fixtures/products';

import { setMode, setProduct, updateForm } from '../../redux/slice';

const mockStore = configureStore(getDefaultMiddleware());

jest.mock('react-redux');
jest.mock('../../api/products');

describe('ProductContainer', () => {
  let store;

  const renderProductContainer = () => render((
    <ProductContainer />
  ));

  beforeEach(() => {
    given('mode', () => 'READ');
  });

  beforeEach(() => {
    store = mockStore(() => ({
      product: productsFixture[0],
      form: productsFixture[0],
      mode: given.mode,
    }));
    useSelector.mockImplementation((selector) => selector(store.getState()));
    useDispatch.mockImplementation(() => store.dispatch);
  });

  it('renders', () => {
    const { container } = renderProductContainer();

    expect(container).toHaveTextContent(productsFixture[0].name);
    expect(container).toHaveTextContent(productsFixture[0].maker);
    expect(container).toHaveTextContent(`${productsFixture[0].price}원`);
  });

  context('when mode is UPDATE', () => {
    beforeEach(() => {
      given('mode', () => 'UPDATE');
    });

    it('renders forms', () => {
      const { getByText } = renderProductContainer();

      expect(getByText('수정')).toBeInTheDocument();
      expect(getByText('취소')).toBeInTheDocument();
    });

    describe('Clicking cancel button', () => {
      it('runs setMode action', () => {
        const { getByText } = renderProductContainer();

        fireEvent.click(getByText('취소'));

        const actions = store.getActions();

        expect(actions[0]).toEqual(setMode('READ'));
      });
    });

    describe('Clicking update button', () => {
      it('requests update product', async () => {
        const { getByText } = renderProductContainer();

        fireEvent.click(getByText('수정'));

        await waitFor(() => {
          const actions = store.getActions();

          expect(setProduct.match(actions[0])).toBe(true);
          expect(setMode.match(actions[1])).toBe(true);
        });
      });
    });

    describe('Changing form', () => {
      it('runs updateForm action', () => {
        const { getByLabelText } = renderProductContainer();

        fireEvent.change(getByLabelText('이름'), { target: { value: '이름' } });

        const actions = store.getActions();

        expect(updateForm.match(actions[0])).toBe(true);
      });
    });
  });

  describe('Clicking 수정하기 button', () => {
    it('runs setMode', () => {
      const { getByText } = renderProductContainer();

      fireEvent.click(getByText('수정하기'));

      const actions = store.getActions();

      expect(actions[0]).toEqual(setMode('UPDATE'));
    });
  });
});
