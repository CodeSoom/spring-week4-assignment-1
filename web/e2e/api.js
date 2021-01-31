const axios = require('axios');

const baseUrl = 'http://localhost:8080/products';

const deleteAll = async () => {
  const { data: products } = await axios.get(baseUrl);
  await Promise.all(
    products
      .map(({ id }) => axios.delete(`${baseUrl}/${id}`)),
  );
};

const create = async (product) => {
  const { data } = await axios.post(baseUrl, product);
  return data;
};

module.exports = {
  deleteAll,
  create,
};
