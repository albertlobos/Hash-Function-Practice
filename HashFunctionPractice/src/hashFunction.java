

import java.util.Scanner;

public class hashFunction
{

    public static void main(String[] args)
    {
        //Instantiated the keys array as a final hardcoded array
        final int[] keys = {1234, 8234, 7867, 1009, 5438, 4312, 3420, 9487, 5418, 5299,
                5078, 8239, 1208, 5098, 5195, 5329, 4543, 3344, 7698, 5412,
                5567, 5672, 7934, 1254, 6091, 8732, 3095, 1975, 3843, 5589,
                5439, 8907, 4097, 3096, 4310, 5298, 9156, 3895, 6673, 7871,
                5787, 9289, 4553, 7822, 8755, 3398, 6774, 8289, 7665, 5523};

        //This is the sentinel loop and the quit boolean. If this is set to false then the loop will end.
        boolean quit = false;
        do
        {
            int[][] Table;
            System.out.println("\n\n\n");
            int choice = menu();
            System.out.println();
            switch (choice) {
                case 1 -> {
                    /*
                    hf1 method is called and is passed the array of keys. It returns a table which is set on the
                    Table object. The table object is then passed to the printHash method to print the table out.
                     */
                    Table = hf1(keys);
                    System.out.println();
                    System.out.println("Hash Table resulted from HF1");
                    System.out.println();
                    printHash(Table);
                }
                case 2 -> {
                    /*
                    hf2 method is called and is passed the array of keys. It returns a table which is set on the
                    Table object. The table object is then passed to the printHash method to print the table out.
                     */
                    Table = hf2(keys);
                    System.out.println();
                    System.out.println("Hash Table resulted from HF2");
                    System.out.println();
                    printHash(Table);
                }
                case 3 -> {
                    /*
                    hf3 method is called and is passed the array of keys. It returns a table which is set on the
                    Table object. The table object is then passed to the printHash method to print the table out.
                     */
                    Table = hf3(keys);
                    System.out.println();
                    System.out.println("Hash Table resulted from HF3");
                    System.out.println();
                    printHash(Table);
                }
                case 4 -> {
                    /*
                    hf4 method is called and is passed the array of keys. It returns a table which is set on the
                    Table object. The table object is then passed to the printHash method to print the table out.
                     */
                    Table = hf4(keys);
                    System.out.println();
                    System.out.println("Hash Table resulted from HF4");
                    System.out.println();
                    printHash(Table);
                }
                case 5 -> quit = true;
            }
        }while (!quit);
    }

    /*
    THe method creates a 2d table then loops through every key given through array. Each key is passed through
    the hash expression and a hash value is then given. The values check to see if the cell is empty(0) and if it is
    then it drops the key there. If a collision occurs it adds one to the index until an empty cell is found.
     */
    public static int[][] hf1(int[] keys)
    {
        int[][] Table = new int[50][2];
        final int m = Table.length;
        for (int key : keys)
        {
            int probes = 0;
            int index = key % m;

            while (Table[index][0] != 0)
            {
                index++;
                if(index > 49)
                {
                    index = 0;
                }
                probes++;
            }

            Table[index][0] = key;
            Table[index][1] = probes;
        }

        return Table;
    }

    /*
    THe method creates a 2d table then loops through every key given through array. Each key is passed through
    the hash expression and a hash value is then given. The values check to see if the cell is empty(0) and if it is
    then it drops the key there. If a collision occurs it i*i to the index. if the cell is still not empty then
    I is incremented by one and squared then added ot the original hash value.
     */
    public static int[][] hf2(int[] keys)
    {
        int[][] Table = new int[50][2];
        final int m = Table.length;

        for (int key : keys)
        {
            int probes = 0;
            int i = 1;
            final int hashValue = key % m;
            int index = hashValue;

            while (Table[index][0] != 0)
            {
                index = (hashValue + (i*i)) % 50;
                i++;
                probes++;
            }

            Table[index][0] = key;
            Table[index][1] = probes;
        }

        return Table;
    }

    /*
    THe method creates a 2d table then loops through every key given through array. Each key is passed through
    the hash expression and a hash value is then given. the key is also passed through the second has expression
    and that is recorded as hashValue2. It checks to see if the first hashValue gives an empty cell. If it
    does then the key is put into the cell and moves on to the next key. If the cell is full using just the
    hashValue, then it goes to a for loop that keeps incrementing the j, incrementing probes, and changing the index.
    In the loop it checks to see if the cell is open every time index is changed. Once it has an index with that
    gives an open cell, it breaks out the for loop. Then it checks to see if j is less than 50 and that the index
    gives an open cell. If it does it will drop the key in there. The last else statement is in case j is 49 and wasn't
    able to find an open cell, printing out that it was "Unable to hash key "+key+" to the table".
     */
    public static int[][] hf3(int[] keys)
    {
        int[][] Table = new int[50][2];
        final int m = Table.length;
        for (int key : keys)
        {
            final int hashValue = key % m;
            int probes = 0;


            if(Table[hashValue][0]==0)
            {
                Table[hashValue][0] = key;
                Table[hashValue][1] = probes;
            }

            else
            {
                final int hashValue2 = 30-(key % 25);
                doubleHash(Table, key, hashValue, hashValue, probes, hashValue2);


            }

        }

        return Table;
    }



    /*
    This method was refactored out of H3 and H4. It calculated the double hash and sets the key and number of probes
    in that row in the hashTable.
     */
    private static void doubleHash(int[][] table, int key, int hashValue, int index, int probes, int hashValue2) {
        int j;
        for(j = 1; j < 50; j++)
        {
            index = (hashValue + (j*hashValue2))%50;
            probes++;
            if (table[index][0]==0)
                break;
        }

        if(j<50 && table[index][0]==0)
        {
            table[index][0] = key;
            table[index][1] = probes;
        }
        else
        {
            System.out.println("Unable to hash key "+key+" to the table");
            System.out.println();
        }
    }

    /*
    This hashing method creates a 2d table. It uses a foreach loop to iterate through every key in the array
    of keys given to us. Every key is given to a method called calculateHashAddressH4(). This method calculates the
    hash value by squaring the key and stored as newKey. From the squared value, the 1st, 2nd and 6th number
    are used to create a new number. 28 is then subtracted from that number until it is less than 50 so that
    IndexOutOfBound exception does not occur. In case of collision I used double hashing. hashValue 2 is
    calculated by subtracting 13 from the original key, sending that key-13 through the calculateHashAddressH4 method.
    The calculateHashAddressH4 method will then return a number that will be subtracted from 59 creating hashValue2.
    The index will then be calculated as (original hashValue + (j(this is the loop iterator) * hashValue2)) %
    50 (for no index out of bounds).If it finds a spot it will break the loop and insert the key to the cell.
    If a spot is not found it will print out "Unable to hash key "+key+" to the table"
     */
    public static int[][] hf4(int[] keys)
    {
        int[][] Table = new int[50][2];
        for (int key : keys)
        {
            final int hashValue = calculateHashAddressH4(key);
            int probes = 0;


            if(Table[hashValue][0]==0)
            {
                Table[hashValue][0] = key;
                Table[hashValue][1] = probes;
            }

            else
            {

                //59 Generates 78 probes with 1 left
                final int hashValue2 = 59-(calculateHashAddressH4(key-13));
                doubleHash(Table, key, hashValue, hashValue, probes, hashValue2);


            }

        }

        return Table;
    }


    /*
    This method calculates the hash value by squaring the key and stored as newKey. From the squared value, the 1st,
    2nd and 6th number are used to create a new number. 28 is then subtracted from that number until it is less than
    50. The newNum is then returned to be used in the hf4 method as the hash value. This will also be used to do double
    hashing in case of collision.
    */
    public static int calculateHashAddressH4(int key){
        int newKey = key*key;
        char[] numArray = Integer.toString(newKey).toCharArray();
        int newNum =numArray[0]+numArray[2]+numArray[5];
        while (newNum>=50)
        {
            //28 gives 78 probes and 1 key left
            newNum-=28;
        }

        return newNum;
    }


    /*
    This method calculates the sum of all the probes in the table. It does this by iterating through all the second
    cells in the array, adding up all the probe numbers and storing it in sumOfProbes. This sumOfProbes is then
    returned to be used in the println();
     */
    public static int sumProbes(int[][] hashTable)
    {
        int sumOfProbes = 0;
        for (int[] ints : hashTable) {
            sumOfProbes += ints[1];
        }

        return sumOfProbes;
    }

    /*
    This method helps print out the Hash table in the correct format. It includes a loop to go through every index
    in the Table. There are if statements just to have the format correct and nice. The if statements just control
    number of spaces that will be needed depending on if the cell is empty and if the index numbers are only 1 digit.
     */
    public static void printHash(int[][] hashTable)
    {
        System.out.println("  Index   Key   Probes");
        System.out.println("------------------------");
        System.out.println();
        for (int i = 0; i < hashTable.length;i++)
        {


            if(i<10)
            {
                if(hashTable[i][0]>0)
                {
                    System.out.println("   "+i+"     "+hashTable[i][0]+"     "+hashTable[i][1]);
                }
                else
                {
                    System.out.println("   "+i+"     0000     0");
                }
            }

            else
            {
                if(hashTable[i][0]>0)
                {
                    System.out.println("  "+i+"     "+hashTable[i][0]+"     "+hashTable[i][1]);
                }
                else
                {
                    System.out.println("  "+i+"     0000     0");
                }
            }

        }


        System.out.println("------------------------");
        System.out.println();
        System.out.println("Sum of Probe Values: "+sumProbes(hashTable));
        System.out.println();

    }

    /*
    This is a simple menu method that prints out 5 options. It will then ask for an input from the user between
    1-5. If another number is entered it will continue asking you to enter a number until it is between 1-5.
    It will then return the choice to the switch statement in main.
     */
    public static int menu()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("-----MAIN MENU--------------------------------------");
        System.out.println("1. Run HF1 (Division method with Linear Probing)");
        System.out.println("2. Run HF2 (Division method with Quadratic Probing)");
        System.out.println("3. Run HF3 (Division method with Double Hashing)");
        System.out.println("4. Run HF4 (Student Designed HF)");
        System.out.println("5. Exit program");
        System.out.println();
        System.out.println("Enter Option number: ");
        System.out.println();
        int choice = sc.nextInt();
        System.out.println();
        while(choice > 5 || choice < 1)
        {
            System.out.println("Please enter a valid number from 1-5");
            choice = sc.nextInt();
        }
        return choice;
    }

}

