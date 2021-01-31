import { isEmpty } from 'lodash';

export default function Products({ products, onClick, onClickDelete }) {
  const handleClick = (id) => () => {
    onClick(id);
  };

  const handleClickDelete = (id) => () => {
    onClickDelete(id);
  };

  if (isEmpty(products)) {
    return (
      <div>
        등록된 상품이 없습니다. 상품을 등록해 주세요.
      </div>
    );
  }

  return (
    products.map(({
      id, name, maker, price,
    }) => (
      <div key={id}>
        <p>{name}</p>
        <p>{maker}</p>
        <p>
          {price}
          원
        </p>
        <button type="button" onClick={handleClick(id)}>상세</button>
        <button type="button" onClick={handleClickDelete(id)}>삭제</button>
      </div>
    ))
  );
}
