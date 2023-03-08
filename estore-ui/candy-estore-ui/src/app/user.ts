export interface User {
    id: number
    username: string
    cart: Map<number,number>
    previousOrder: Map<number, number>
}