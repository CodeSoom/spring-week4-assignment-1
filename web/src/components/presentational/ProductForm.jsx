import Input from './Input';

export default function ProductForm({
  buttonText = '등록',
  product,
  onChange,
  onSubmit,
}) {
  const {
    name, maker, price, imageUrl,
  } = product;

  const handleSubmit = (e) => {
    e.preventDefault();

    onSubmit();
  };

  return (
    <form onSubmit={handleSubmit}>
      <Input
        id="name"
        label="이름"
        placeholder="이름을 입력해주세요"
        value={name}
        onChange={onChange}
      />
      <Input
        id="maker"
        label="메이커"
        placeholder="메이커를 입력해주세요"
        value={maker}
        onChange={onChange}
      />
      <Input
        id="price"
        label="가격"
        type="number"
        placeholder="가격을 입력해주세요"
        value={price}
        onChange={onChange}
      />
      <Input
        id="imageUrl"
        label="이미지"
        type="text"
        placeholder="이미지 URL을 입력해주세요"
        value={imageUrl}
        onChange={onChange}
      />
      <button type="submit">{buttonText}</button>
    </form>
  );
}
