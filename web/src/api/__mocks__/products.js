import { productsFixture } from '../../fixtures/products';

export const fetchProducts = async () => productsFixture;

export const fetchProduct = async () => productsFixture[0];

export const postProduct = async () => productsFixture[0];

export const deleteProduct = async () => {};

export const putProduct = async () => productsFixture[0];
