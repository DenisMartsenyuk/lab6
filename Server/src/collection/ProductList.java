package collection;

import elements.Product;
import elements.UnitOfMeasure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ProductList {

    private ArrayList<Product> products;
    private HandlerId handlerId;

    public ProductList() {
        products = new ArrayList<>();
        handlerId = new HandlerId();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String printInfo() {
        return "Тип коллекции: " + products.getClass() + ", Размер: " + products.size();
    }

    public String add(Product item) {
        item.setId(handlerId.provideId());
        item.setManufacturerId(handlerId.provideId());
        item.setCreationDate();
        products.add(item);
        return "Элемент добавлен.";
    }

    public String addIfMin(Product item) {
        double price = Collections.min(products, new Comparator<Product>() {
            @Override
            public int compare(Product product, Product t1) {
                return Double.compare(product.getPrice(), t1.getPrice());
            }
        }).getPrice();
        if (price > item.getPrice()) {
            add(item);
            return "Элемент наименьший, поэтому он добавлен.";
        }
        return "Элемент не является наименьшим, поэтому он добавлен не был.";
    }

    public String reverse() {
        Collections.reverse(products);
        return "Коллекция выставлена в обратном порядке.";
    }

    public String clear() {
        handlerId.clear();
        products.clear();
        return "Коллекция очищенна.";
    }

    public String removeById(int id) {
        if (!handlerId.contains(id))
            return "Элемент Product с таким id не найден.";
        for (Product product : products) {
            if(product.getId() == id) {
                remove(product);
                return "Элемент Product с таким id удален.";
            }
            if(product.getManufacturer().getId() == id) {
                return "Вы не можете удалить \"manufacturer\", потому что оно не может быть \"Null\".";
            }
        }
        return "";
    }

    public void remove(int index) {
        handlerId.removeId(products.get(index).getId());
        products.remove(index);
    }

    public void remove(Product product) {
        handlerId.removeId(product.getId());
        products.remove(product);
    }

    public String removeFirst() {
        try {
            remove(0);
            return "Первый элемент коллекции удален.";
        }
        catch (IndexOutOfBoundsException e) {
            return "Коллекция пуста, первого элемент удалить не удалось.";
        }
    }

    public String updateById(int id, Product item) {
        try {
            products.stream().filter(product -> product.getId() == id).findFirst().get().updateProduct(item);
            return "Элемент Product с введенным id заменен.";
        } catch (Exception e) {
            return "Элемент Product с таким id не найден.";
        }
    }

    public String removeAllByManufactureCost(Long manufactureCost) {
        products = (ArrayList<Product>) products.stream().filter(product -> !product.getManufactureCost().equals(manufactureCost))
                .collect(Collectors.toList());
        return "Команда выполнена!";
    }

    public String printLessThanUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == UnitOfMeasure.SQUARE_METERS || unitOfMeasure == UnitOfMeasure.MILLIGRAMS || unitOfMeasure == UnitOfMeasure.MILLILITERS)
            return "Искомых элементов нет.";
        ArrayList<Product> result = (ArrayList<Product>) products.stream().filter((product -> unitOfMeasure.ordinal() >
                product.getUnitOfMeasure().ordinal() && unitOfMeasure.ordinal() - product.getUnitOfMeasure().ordinal() == 1)).collect(Collectors.toList());
        return getAlphabet(result);
    }

    public void sort() {
        products = (ArrayList<Product>) products.stream().sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())).collect(Collectors.toList());
    }

    public String getAlphabet() {
        return getAlphabet(new ArrayList<>(products));
    }

    public String getAlphabet(ArrayList<Product> srcProducts) {
        srcProducts = (ArrayList<Product>) srcProducts.stream()
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
        return srcProducts.toString();
    }

    public void sort(Comparator<Product> comparator) {
        products = (ArrayList<Product>) products.stream().sorted(comparator).collect(Collectors.toList());
    }

    public String printAscending() {
        ArrayList<Product> result = (ArrayList<Product>) new ArrayList<>(products).stream()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice())).collect(Collectors.toList());
        return result.toString();
    }
}
