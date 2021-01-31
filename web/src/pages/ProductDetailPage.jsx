import { useEffect } from 'react';

import { useParams } from 'react-router-dom';

import { useDispatch } from 'react-redux';

import ProductContainer from '../components/containers/ProductContainer';

import { loadProduct } from '../redux/slice';

export default function ProductDetailPage() {
  const dispatch = useDispatch();

  const { id } = useParams();

  useEffect(() => {
    dispatch(loadProduct(id));
  }, []);

  return (
    <>
      <h2>고양이 장난감 상세</h2>
      <ProductContainer />
    </>
  );
}
