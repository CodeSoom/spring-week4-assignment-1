import { useDispatch, useSelector } from 'react-redux';

import ProductDetail from '../presentational/ProductDetail';
import ProductForm from '../presentational/ProductForm';

import { setMode, updateProduct, updateForm } from '../../redux/slice';

export default function ProductContainer() {
  const dispatch = useDispatch();
  const { product, form, mode } = useSelector((state) => state);

  const handleClickUpdate = () => {
    dispatch(setMode('UPDATE'));
  };

  const handleClickCancel = () => {
    dispatch(setMode('READ'));
  };

  const handleChange = (value) => {
    dispatch(updateForm(value));
  };

  const handleSubmit = () => {
    dispatch(updateProduct());
  };

  return (
    <>
      { mode === 'READ' && (
        <ProductDetail
          product={product}
          onClickUpdate={handleClickUpdate}
        />
      )}
      { mode === 'UPDATE' && (
        <div>
          <ProductForm
            buttonText="수정"
            product={form}
            onChange={handleChange}
            onSubmit={handleSubmit}
          />
          <button type="button" onClick={handleClickCancel}>취소</button>
        </div>
      )}
    </>
  );
}
