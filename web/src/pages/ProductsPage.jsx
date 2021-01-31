import { useEffect } from 'react';

import { useHistory } from 'react-router-dom';

import { useDispatch, useSelector } from 'react-redux';

import ProductsContainer from '../components/containers/ProductsContainer';

import { loadProducts, clearSelectedProductId } from '../redux/slice';

export default function ProductsPage() {
  const history = useHistory();
  const dispatch = useDispatch();

  const { selectedProductId } = useSelector((state) => state);

  const handleClick = () => {
    history.push('/products/product');
  };

  useEffect(() => {
    dispatch(loadProducts());
  }, []);

  useEffect(() => {
    if (selectedProductId) {
      history.push(`/products/${selectedProductId}`);
      dispatch(clearSelectedProductId());
    }
  }, [selectedProductId]);

  return (
    <>
      <h2>고양이 장난감 목록</h2>
      <button type="button" onClick={handleClick}>
        등록하기
      </button>
      <ProductsContainer />
    </>
  );
}
