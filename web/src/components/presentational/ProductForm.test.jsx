import { render, fireEvent } from '@testing-library/react';

import { productsFixture } from '../../fixtures/products';

import ProductForm from './ProductForm';

describe('ProductForm', () => {
  const handleChange = jest.fn();
  const handleSubmit = jest.fn();

  const renderProductForm = () => render((
    <ProductForm
      buttonText={given.buttonText}
      product={given.product}
      onChange={handleChange}
      onSubmit={handleSubmit}
    />
  ));

  beforeEach(() => {
    jest.clearAllMocks();
  });

  beforeEach(() => {
    given('product', () => productsFixture[0]);
  });

  it('renders', () => {
    const { getByLabelText } = renderProductForm();

    expect(getByLabelText('이름')).toBeInTheDocument();
    expect(getByLabelText('메이커')).toBeInTheDocument();
    expect(getByLabelText('가격')).toBeInTheDocument();
    expect(getByLabelText('이미지')).toBeInTheDocument();
  });

  it('renders value', () => {
    const { getByDisplayValue } = renderProductForm();

    expect(getByDisplayValue(given.product.name)).toBeInTheDocument();
    expect(getByDisplayValue(given.product.maker)).toBeInTheDocument();
    expect(getByDisplayValue(given.product.price)).toBeInTheDocument();
    expect(getByDisplayValue(given.product.imageUrl)).toBeInTheDocument();
  });

  context('when button text is given', () => {
    beforeEach(() => {
      given('buttonText', () => '수정');
    });

    it('renders button with text', () => {
      const { getByText } = renderProductForm();

      expect(getByText('수정')).toBeInTheDocument();
    });
  });

  describe('Changing value', () => {
    it('calls onChange handler', () => {
      const { getByLabelText } = renderProductForm();

      fireEvent.change(getByLabelText('이름'), {
        target: { value: '떼껄룩' },
      });

      expect(handleChange).toBeCalledWith({
        name: 'name',
        value: '떼껄룩',
      });
    });
  });

  describe('Clicking submit button', () => {
    it('calls onSubmit handler', () => {
      const { getByText } = renderProductForm();

      fireEvent.click(getByText('등록'));

      expect(handleSubmit).toBeCalled();
    });
  });
});
