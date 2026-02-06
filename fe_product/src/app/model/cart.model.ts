export interface CartItemRes {
  productId: number;
  productName: string;
  quantity: number;
  priceAtAdd: number;
}

export interface CartRes {
  user_id: number;
  items: CartItemRes[];
  totalPrice: number;
}
