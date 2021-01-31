import { fireEvent, render } from '@testing-library/react';

import ProductDetail from './ProductDetail';

import { productsFixture } from '../../fixtures/products';

describe('ProductDetail', () => {
  const handleClickUpdate = jest.fn();

  const renderProductDetail = () => render((
    <ProductDetail
      product={given.product}
      onClickUpdate={handleClickUpdate}
    />
  ));

  beforeEach(() => {
    jest.clearAllMocks();
  });

  beforeEach(() => {
    given('product', () => productsFixture[0]);
  });

  it('renders', () => {
    const { container } = renderProductDetail();

    expect(container).toHaveTextContent(productsFixture[0].name);
    expect(container).toHaveTextContent(productsFixture[0].maker);
    expect(container).toHaveTextContent(`${productsFixture[0].price}원`);
  });

  describe('Clicking update button', () => {
    it('calls onClickUpdate handler', () => {
      const { getByText } = renderProductDetail();

      fireEvent.click(getByText('수정하기'));

      expect(handleClickUpdate).toBeCalled();
    });
  });
});
