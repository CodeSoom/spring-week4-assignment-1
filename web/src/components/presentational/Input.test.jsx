import { render, fireEvent } from '@testing-library/react';

import Input from './Input';

describe('Input', () => {
  const id = 'name';
  const placeholder = '이름을 입력해주세요';
  const label = '이름';
  const value = '애옹이';

  const handleChange = jest.fn();

  const renderInput = () => render((
    <Input
      id={id}
      label={label}
      placeholder={placeholder}
      value={value}
      onChange={handleChange}
    />
  ));

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders', () => {
    const {
      getByLabelText,
      getByDisplayValue,
      getByPlaceholderText,
    } = renderInput();

    expect(getByLabelText(label)).toBeInTheDocument();
    expect(getByDisplayValue(value)).toBeInTheDocument();
    expect(getByPlaceholderText(placeholder)).toBeInTheDocument();
  });

  describe('Changing value', () => {
    it('calls onChange handler', () => {
      const { getByLabelText } = renderInput();

      fireEvent.change(getByLabelText(label), {
        target: { value: '떼껄룩' },
      });

      expect(handleChange).toBeCalledWith({
        name: id,
        value: '떼껄룩',
      });
    });
  });
});
