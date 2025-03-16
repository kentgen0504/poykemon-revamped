abstract class Item {
    protected static final int MAX_QUANTITY = 99;

    protected String name;
    protected int quantity;
    protected boolean disposable;

    public Item(String name, int quantity, boolean disposable){
        this.name = name;
        this.quantity = quantity;
        this.disposable = disposable;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public abstract void useItem();
}

abstract class Poykeball extends Item{
    private int capturePower;

    public Poykeball(String name, int quantity, int capturePower){
        super(name, quantity, true);
    }

    public int getCapturePower(){
        return this.capturePower;
    }
}

class Basicball extends Poykeball{

    public Basicball(int quantity){
        super("Basicball", quantity, 5);
    }

    @Override
    public void useItem(){
        System.out.println(name + " has been used.");

        quantity--;

        if (quantity == 0){

        }
        
    }
}

class Greatball extends Poykeball{

    public Greatball(int quantity){
        super("Greatball", quantity, 10);
    }

    @Override
    public void useItem(){
        System.out.println(name + " has been used.");

        quantity--;
    }
}

abstract class Bait extends Item{
    private int baitPower;

    public Bait(String name, int quantity, int baitPower){
        super(name, quantity, true);
    }

    public int getBaitPower(){
        return this.baitPower;
    }
}

class ChipBerry extends Bait{

    public ChipBerry(int quantity){
        super("Chip Berry", quantity, 5);
    }

    @Override
    public void useItem(){
        System.out.println(name + " has been used.");

        quantity--;
    }
}

class Gadget{

}

class Important{

}