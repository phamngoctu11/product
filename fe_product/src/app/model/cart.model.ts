export interface CartItemRes {
  productId: number;
  productName: string;
  quantity: number;
  priceAtAdd: number;
}

export interface CartRes {
  userId: number;
  items: CartItemRes[];
  totalPrice: number;
}
