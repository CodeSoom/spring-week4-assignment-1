import React from 'react';
import { useDispatch, useSelector } from 'react-redux';

import ProductForm from '../presentational/ProductForm';

import { registerProduct, updateForm } from '../../redux/slice';

export default function FormContainer() {
  const { form } = useSelector((state) => state);

  const dispatch = useDispatch();

  const handleChange = (value) => {
    dispatch(updateForm(value));
  };

  const handleSubmit = () => {
    dispatch(registerProduct());
  };

  return (
    <ProductForm
      product={form}
      onChange={handleChange}
      onSubmit={handleSubmit}
    />
  );
}
