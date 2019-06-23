public final class Product{ // no sun class
	private final String no;
	private final String name;
	private final double price;

	public Product(String no, String name, double price){
		super();
		this.no = no;
		this.name = name;
		this.price = price;
	}

	public String getNo(){return no;}
	public String getName(){return name;}
	public String getPrice(){return price;}
}
