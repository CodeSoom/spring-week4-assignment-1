import React from 'react';

import { useDispatch, useSelector } from 'react-redux';

import Products from '../presentational/Products';

import { selectProduct, removeProduct } from '../../redux/slice';

export default function ProductsContainer() {
  const { products } = useSelector((state) => state);

  const dispatch = useDispatch();

  const handleClick = (id) => {
    dispatch(selectProduct(id));
  };

  const handleClickDelete = (id) => {
    dispatch(removeProduct(id));
  };

  return (
    <Products
      products={products}
      onClick={handleClick}
      onClickDelete={handleClickDelete}
    />
  );
}
