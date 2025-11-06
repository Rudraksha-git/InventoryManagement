class InsufficientStockException extends Exception {
    public InsufficientStockException (String message) {
        System.out.println("Insufficient Stock. Please choose a smaller quantity");
    }
}