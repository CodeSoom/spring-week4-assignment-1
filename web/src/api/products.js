import axios from 'axios';

export const fetchProducts = async () => {
  const { data } = await axios.get('http://localhost:8080/products');
  return data;
};

export const fetchProduct = async (id) => {
  const { data } = await axios.get(`http://localhost:8080/products/${id}`);
  return data;
};

export const postProduct = async (product) => {
  const { data } = await axios.post('http://localhost:8080/products', product);
  return data;
};

export const deleteProduct = async (id) => {
  await axios.delete(`http://localhost:8080/products/${id}`);
};

export const putProduct = async (product) => {
  const { data } = await axios
    .patch(`http://localhost:8080/products/${product.id}`, product);
  return data;
};
