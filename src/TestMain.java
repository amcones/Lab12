import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class Canvas
{
    ArrayList<Shape> shapes=new ArrayList<>();
    void addShape(Shape s)
    {
        System.out.print("正在画一个");
        if(s instanceof Circle) System.out.print("圆形....");
        else if(s instanceof Rectangle) System.out.print("长方形....");
        System.out.println(s);
        shapes.add(s);
    }
    void removeShape(Shape s)
    {
        System.out.println("正在删除"+s.toString());
        shapes.removeIf(shape -> shape == s);
    }
    void clone(Shape s)
    {
        addShape(s.clone());
    }
    Shape Max()
    {
        Shape maxShape=shapes.get(0);
        for(Shape shape:shapes)
        {
            if(shape.getArea()>maxShape.getArea()){
                maxShape=shape;
            }
        }
        return maxShape;
    }
    public double getArea()
    {
        double area=0;
        for(Shape shape:shapes)
        {
            area += shape.getArea();
        }
        return area;
    }
    void printAllShapes()
    {
        for (Shape shape:shapes) {
            System.out.println(shape);
        }
    }
}
abstract class Shape implements Comparable,Cloneable
{
    Date createdDate;
    String color;

    public void setColor(String color) {
        this.color = color;
    }

    public Shape(String color)
    {
        this.createdDate=new Date();
        this.color=color;
    }
    abstract double getArea();

    abstract void draw();

    @Override
    public int compareTo(Object o) {
        if(o instanceof Shape shape){
            return Double.compare(this.getArea(), shape.getArea());
        }
        else {
            throw new RuntimeException("传入的数据类型不是Shape");
        }
    }
    @Override
    public Shape clone()
    {
        Shape shape=null;
        try{
            shape=(Shape)super.clone();
            shape.createdDate= new Date();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return shape;
    }
}
class Circle extends Shape
{
    double radius;
    public Circle(String color,double radius)
    {
        super(color);
        this.radius=radius;
    }
    @Override
    public String toString()
    {
        return "Circle [radius="+radius+","+
                "getCreatedDate()="+createdDate+","+
                "getColor()="+color+
                "]";
    }

    @Override
    double getArea() {
        return Math.PI*radius*radius;
    }

    @Override
    void draw() {
        System.out.print("正在画一个圆形....");
        System.out.println(this);
    }
}
class Rectangle extends Shape
{
    double length,width;
    public Rectangle(String color,double width,double length)
    {
        super(color);
        this.length=length;
        this.width=width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Rectangle [" +
                "width=" + width + "," +
                "length=" + length + "," +
                "getCreatedDate()=" + createdDate + "," +
                "getColor()=" + color +
                ']';
    }

    @Override
    double getArea() {
        return length*width;
    }

    @Override
    void draw() {
        System.out.print("正在画一个长方形....");
        System.out.println(this);
    }
}
public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        //创建c1圆的对象
        Circle c1 = new Circle("yellow",1.0);
        //创建c2圆的对象
        Circle c2 = new Circle("white",2.0);
        //创建r1矩形的对象
        Rectangle r1 = new Rectangle("pick", 3, 4);
        //创建r2矩形的对象
        Rectangle r2 = new Rectangle("black", 2, 5);
        //创建c画布的对象
        Canvas c = new Canvas();
        //在画布上加入并绘制c1,c2,r1和r2
        c.addShape(c1);
        c.addShape(c2);
        c.addShape(r1);
        c.addShape(r2);
        System.out.println();
        //在画布上删除c1对象
        c.removeShape(c1);
        c.printAllShapes();
        System.out.println();
        //为了拉开时间差，等待2秒
        TimeUnit.SECONDS.sleep(2);
        //复制r1对象赋值给r3并加入在画布中
        Rectangle r3 =(Rectangle)(r1.clone());
        c.addShape(r3);
        //修改r1对象的属性
        r1.setColor("green");
        r1.setLength(20);
        r1.setWidth(10);
        // 注意r3和r1输出结果的不同，长，宽和日期
        c.printAllShapes();
        System.out.println();
        //求所有形状中面积最大的并打印其信息
        Shape p = c.Max();
        System.out.println(p.toString());
    }
}