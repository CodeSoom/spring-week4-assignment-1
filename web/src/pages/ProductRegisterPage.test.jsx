import { useDispatch, useSelector } from 'react-redux';
import { getDefaultMiddleware } from '@reduxjs/toolkit';

import { render } from '@testing-library/react';
import configureStore from 'redux-mock-store';

import ProductRegisterPage from './ProductRegisterPage';

import { initializeStatus } from '../redux/slice';

const mockStore = configureStore(getDefaultMiddleware());

const mockPush = jest.fn();

jest.mock('react-redux');
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useHistory() {
    return { push: mockPush };
  },
}));

describe('ProductRegisterPage', () => {
  let store;

  const renderProductRegisterPage = () => render((
    <ProductRegisterPage />
  ));

  beforeEach(() => {
    given('status', () => null);
  });

  beforeEach(() => {
    store = mockStore(() => ({
      form: {
        name: '',
        maker: '',
        price: '',
      },
      status: given.status,
    }));
    useSelector.mockImplementation((selector) => selector(store.getState()));
    useDispatch.mockImplementation(() => store.dispatch);
  });

  it('renders', () => {
    const { container, getByLabelText } = renderProductRegisterPage();

    expect(container).toHaveTextContent('고양이 장난감 등록');
    expect(getByLabelText('이름')).toBeInTheDocument();
    expect(getByLabelText('메이커')).toBeInTheDocument();
    expect(getByLabelText('가격')).toBeInTheDocument();
  });

  context('when status is succss', () => {
    beforeEach(() => {
      given('status', () => ({ type: 'SUCCESS' }));
    });

    it('goes to products page', () => {
      renderProductRegisterPage();

      const actions = store.getActions();

      expect(mockPush).toBeCalled();
      expect(initializeStatus.match(actions[0])).toBe(true);
    });
  });
});
