export default function ProductDetail({
  product,
  onClickUpdate,
}) {
  const {
    name, maker, price, imageUrl,
  } = product;
  return (
    <>
      <p>{name}</p>
      <p>{maker}</p>
      <p>
        {price}
        원
      </p>
      <img style={{ width: '100%' }} src={imageUrl} alt="장난감 이미지" />
      <button type="button" onClick={onClickUpdate}>
        수정하기
      </button>
    </>
  );
}
