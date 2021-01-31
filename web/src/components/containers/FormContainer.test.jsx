import { useDispatch, useSelector } from 'react-redux';
import { getDefaultMiddleware } from '@reduxjs/toolkit';

import { render, fireEvent, waitFor } from '@testing-library/react';
import configureStore from 'redux-mock-store';

import FormContainer from './FormContainer';

import { clearForm, success, updateForm } from '../../redux/slice';

const mockStore = configureStore(getDefaultMiddleware());

jest.mock('react-redux');
jest.mock('../../api/products');

describe('FormContainer', () => {
  const name = '장난감 뱀';
  const maker = '애옹이네 장난감';
  const price = 5000;

  let store;

  const renderFormContainer = () => render((
    <FormContainer />
  ));

  beforeEach(() => {
    store = mockStore(() => ({
      form: {
        name,
        maker,
        price,
      },
    }));
    useSelector.mockImplementation((selector) => selector(store.getState()));
    useDispatch.mockImplementation(() => store.dispatch);
  });

  it('renders', () => {
    const { getByDisplayValue } = renderFormContainer();

    expect(getByDisplayValue(name)).toBeInTheDocument();
    expect(getByDisplayValue(maker)).toBeInTheDocument();
    expect(getByDisplayValue(price)).toBeInTheDocument();
  });

  describe('Changing value', () => {
    const value = '떼껄룩';

    it('runs updateForm action', () => {
      const { getByLabelText } = renderFormContainer();

      fireEvent.change(getByLabelText('이름'), {
        target: { value },
      });

      const actions = store.getActions();

      expect(actions[0]).toEqual(updateForm({
        name: 'name',
        value,
      }));
    });
  });

  describe('Clicking submit button', () => {
    it('runs registerProduct', async () => {
      const { getByText } = renderFormContainer();

      fireEvent.click(getByText('등록'));

      await waitFor(() => {
        const actions = store.getActions();

        expect(clearForm.match(actions[0])).toBe(true);
        expect(success.match(actions[1])).toBe(true);
      });
    });
  });
});
