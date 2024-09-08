import { defineStore } from 'pinia';

// initState와 updateLocalStorage 함수는 그대로 유지
function initState() {
    return {
        cartItems: JSON.parse(localStorage.getItem('cartItems')) || [],
        totalQuantity: localStorage.getItem('totalQuantity') || 0
    };
}

function updateLocalStorage(cartItems, totalQuantity) {
    localStorage.setItem('cartItems', JSON.stringify(cartItems));
    localStorage.setItem('totalQuantity', totalQuantity);
}

// Pinia 스토어 정의
export const useCartStore = defineStore('cart', {
    state: () => initState(),
    actions: {
        addToCart(item) {
            const existItem = this.cartItems.find(i => i.itemId == item.itemId);
            if (existItem) {
                existItem.count += item.count;
            } else {
                this.cartItems.push(item);
            }
            this.totalQuantity = parseInt(this.totalQuantity) + item.count;
            updateLocalStorage(this.cartItems, this.totalQuantity);
        },
        clearCart() {
            console.log("clearCart");
            console.log(this.cartItems);
            this.cartItems = [];
            this.totalQuantity = 0;
            updateLocalStorage(this.cartItems, this.totalQuantity);
        }
    },
    getters: {
        getCartItems: (state) => state.cartItems,
        getTotalQuantity: (state) => state.totalQuantity
    }
});