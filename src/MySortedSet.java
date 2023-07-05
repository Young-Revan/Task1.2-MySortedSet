import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class MySortedSet<T extends Comparable>
{
    // Подкласс "Узел"
    private class Node<T extends Comparable>
    {
        public T data;
        public Node<T> parent;
        public Node<T> left;
        public Node<T> right;

        public int height;
        public Node(T data)
        {
            this.data = data;
            this.parent = null;
            this.height = 0;
            this.left = null;
            this.right = null;
        }

        public boolean add(T element)
        {
            if (data.compareTo(element) > 0)            // Если вставляемый объект МЕНЬШЕ элемента в данном узле
            {
                if (left == null)
                {
                    // Добавляем узел
                    left = new Node(element);
                    left.parent = this;

                    // Пересчёт высоты узла
                    updateHeight();
                    balance();
                    return true;
                }
                else
                {
                    // Если вставка прошла успешно, то обновляем высоту узла
                    if (left.add(element))
                    {
                        // Пересчёт высоты узла
                        updateHeight();
                        balance();
                        return true;
                    }
                    else return false;
                }
            }
            else if (data.compareTo(element) < 0)       // Если вставляемый объект БОЛЬШЕ элемента в данном узле
            {
                if (right == null)
                {
                    right = new Node(element);
                    right.parent = this;

                    updateHeight();
                    balance();
                    return true;
                }
                else
                {
                    // Если вставка прошла успешно, то обновляем высоту узла
                    if (right.add(element))
                    {
                        updateHeight();
                        balance();
                        return true;
                    }
                    else return false;
                }
            }
            else                                        // Если вставляемый элемент равен элементу в данном узле (полное совпадение)
            {
                return true;
            }

            //return false;
        }

        public void delete(Node<T> node)
        {
            //Node<T> del = node;
            //node = node.parent;
            // Если удаляемый узел является корнем и без потомков, то просто обнуляем его
            if (node == root && node.isLeaf())
            {
                root = null;
            }

            if (node.left != null)
            {
                Node<T> leftMax = getMax(node.left);
                node.data = leftMax.data;
                delete(leftMax);
            }
            else if (node.right != null)
            {
                Node<T> rightMin = getMin(node.right);
                node.data = rightMin.data;
                delete(rightMin);
            }
            else
            {
                if (node.whichSon() == -1) node.parent.left = null;
                else if (node.whichSon() == 1) node.parent.right = null;
                node = node.parent;
                while (node != null)
                {
                    node.updateHeight();
                    node.balance();
                    node = node.parent;
                }
            }
        }

        boolean isLeaf()
        {
            return left == null && right == null;
        }

        int whichSon()
        {
            if (parent == null) return 0;
            else if (parent.left == this) return -1;
            else if (parent.right == this) return 1;
            return 0;
        }

        Node<T> getMin(Node<T> from)
        {
            if (from.left == null) return from;
            return getMin(from.left);
        }

        Node<T> getMax(Node<T> from)
        {
            if (from.right == null) return from;
            return getMax(from.right);
        }

        int getHeight(Node<T> node)
        {
            if (node == null) return -1;
            else return node.height;
        }

        int getBalance(Node<T> node)
        {
            if (node == null) return 0;
            else return getHeight(node.right) - getHeight(node.left);
        }

        void updateHeight()
        {
            height = Math.max(getHeight(left), getHeight(right)) + 1;
        }

        void swap(Node<T> first, Node<T> second)
        {
            T tmp = first.data;
            first.data = second.data;
            second.data = tmp;
        }

        void leftTurn(Node<T> node)
        {
            // Сохраняем левого потомка
            Node<T> tmp = node.left;

            // Меняем местами данные в узлах
            swap(node, node.right);

            // Теперь левый потомок будет стоять справа
            node.left = node.right;

            // А правый внук левого потомка - справа
            node.right = node.left.right;
            if (node.right != null) node.right.parent = node;

            // Левого внука поворачиваемого узла делаем правым внуком
            node.left.right = node.left.left;

            // Не забываем про ранее сохранённого потомка
            node.left.left = tmp;
            if (tmp != null) tmp.parent = node.left;

            // Обновление высот повёрнутых узлов
            node.updateHeight();
            node.left.updateHeight();
        }

        void rightTurn(Node<T> node)
        {
            // Сохраняем правого потомка
            Node<T> tmp = node.right;

            // Меняем местами данные в узлах
            swap(node, node.left);

            // Теперь левый потомок будет стоять справа
            node.right = node.left;

            // А левый внук правого потомка - слева
            node.left = node.right.left;
            if (node.left != null) node.left.parent = node;

            // Правого внука поворачиваемого узла делаем левым внуком
            node.right.left = node.right.right;

            // Не забываем про ранее сохранённого потомка
            node.right.right = tmp;
            if (tmp != null) tmp.parent = node.right;

            // Обновление высот повёрнутых узлов
            node.updateHeight();
            node.right.updateHeight();
        }

        void balance()
        {
            int balance = getBalance(this);
            if (balance == -2)
            {
                if (getBalance(left) == 1) leftTurn(left);
                rightTurn(this);
            }
            else if (balance == 2)
            {
                if (getBalance(right) == -1) rightTurn(right);
                leftTurn(this);
            }
        }

        public void printAsTreeNode(String offset)
        {
            System.out.print(offset + data + '(' + height + ')' + '\n');
            if (left != null) left.printAsTreeNode(offset + "\t");
            else System.out.print(offset + "\tnull(-1)\n");
            if (right != null) right.printAsTreeNode(offset + "\t");
            else System.out.print(offset + "\tnull(-1)\n");
        }

        public void print()
        {
            if (left != null) left.print();
            System.out.print(data);
            System.out.print('\t');
            if (right != null) right.print();
        }
    }

    public class MyIterator implements Iterator<T> {
        // Это поле хранит ссылку на "следующий" узел
        Node<T> currentNode;

        // Это поле хранит ссылку на предыдущий узел, при подъёме наверх
        Node<T> pastNode;

        public MyIterator()
        {
            currentNode = root;
            getMin();
        }

        MyIterator(Node<T> position)
        {
            currentNode = position;
        }


        @Override
        public void remove() {
            Iterator.super.remove();
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next()
        {
            T e = currentNode.data;
            goToNext();
            return e;
        }

        // Переход к следующему узлу (вызывать его заранее!)
        void goToNext()
        {
            // Надо иметь ввиду, что если мы находимся в данном узле, то слева уже точно были


            // Если есть ещё узел внизу справа (слева их не может уже быть),
            // то спускаемся на уровень вниз-направо, и снова идём максимально вниз-влево
            if (currentNode.right != null)
            {
                currentNode = currentNode.right;
                getMin();
            }
            // Если справа ничего нет, то поднимаемся на уровень наверх
            else
            {
                // Запоминаем прошлый узел
                pastNode = currentNode;
                currentNode = currentNode.parent;

                // Если мы были в корне, а теперь поднялись над ним, то всё, гг вп
                if (currentNode == null) return;

                // Если мы поднялись слева, то ничего не делаем, текущий узел будет "следующим"
                if (currentNode.left == pastNode) return;

                // Поднимаемся наверх до тех пор, пока не обнаружим, что поднялись не справа, а слева
                // ( ну или пока не выскочим из дерева нафиг )
                while (currentNode != null && currentNode.right == pastNode)
                {
                    // Запоминаем прошлый узел
                    pastNode = currentNode;
                    // Поднимаемся на уровень выше
                    currentNode = currentNode.parent;
                }
            }
        }
        public void goTo(Node<T> node)
        {
            currentNode = node;
        }

        // "Пронзаем" дерево максимально вниз и влево относительно текущего узла
        void getMin()
        {
            if (currentNode == null) return;

            while (currentNode.left != null)
            {
                currentNode = currentNode.left;
            }
        }
    }

    // Само дерево

    Node<T> root;

    public MySortedSet()
    {
        root = null;
    }

    public boolean add(T element)
    {
        if (root == null)
        {
            root = new Node(element);
            return true;
        }
        else
        {
            return root.add(element);
        }

        //return false;
    }

    public void delete(T element)
    {
        if (root == null) return;

        Node<T> node = find(root, element);
        node.delete(node);
    }

    Node<T> find(Node<T> node, T element)
    {
        if (node.data.compareTo(element) == 0) return node;

        // Если искомый элемент меньше текущего
        if (node.data.compareTo(element) > 0) return find(node.left, element);
        else if (node.data.compareTo(element) < 0) return find(node.right, element);
        return null;
    }


    public boolean contains(T element)
    {
        if (root == null) return false;

        Node<T> node = find(root, element);

        if (node != null) return true;
        return false;
    }

    public MySortedSet<T> subSet(T fromElement, T toElement)
    {
        Node<T> from = find(root, fromElement);
        MyIterator iter = new MyIterator(from);
        MySortedSet<T> subset = new MySortedSet<T>();
        while (iter.hasNext())
        {
            T el = iter.next();
            if (el.compareTo(toElement) > 0) break;
            subset.add(el);
        }
        return subset;
    }

    public MySortedSet<T> headSet(T element)
    {
        return subSet(first(), element);
    }

    public MySortedSet<T> tailSet(T element)
    {
        return subSet(element, last());
    }

    public T first()
    {
        if (root == null) return null;
        return root.getMin(root).data;
    }

    public T last()
    {
        if (root == null) return null;
        return root.getMax(root).data;
    }

    Iterator<T> iterator()
    {
        return new MyIterator();
    }

    public void printAsTree()
    {
        if (root == null)
        {
            System.out.print("Пустое множество");
        }
        else
        {
            root.printAsTreeNode("");
        }
    }

    public void print()
    {
        if (root == null)
        {
            System.out.print("Пустое множество");
        }
        else
        {
            root.print();
        }
    }
}
