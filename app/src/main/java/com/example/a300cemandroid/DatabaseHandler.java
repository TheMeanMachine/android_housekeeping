package com.example.a300cemandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context dbcontext;

    public DatabaseHandler(Context context) {

        super(context, "houseDB", null, 1);
        this.dbcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL("CREATE TABLE IF NOT EXISTS user (" +
                "_id integer primary key autoincrement," +
                "firstName," +
                "lastName," +
                "email," +
                "img" +
                ")"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS house (" +
                "_id integer primary key autoincrement," +
                "houseName," +
                "headOfHouseID integer," +
                "long," +
                "lat," +
                "FOREIGN KEY(headOfHouseID) REFERENCES user(ID)" +
                ")"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS houseMembers (" +
                "_id integer primary key autoincrement," +
                "houseID integer," +
                "userID integer," +
                "FOREIGN KEY(houseID) REFERENCES house(ID)," +
                "FOREIGN KEY(userID) REFERENCES user(ID)" +
                ")"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS task (" +
                "_id integer primary key autoincrement," +
                "houseID integer," +
                "userID integer," +
                "completed," +
                "datemade," +
                "timemade," +
                "title," +
                "FOREIGN KEY(userID) REFERENCES user(ID)" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Adds house to db
     * @param house - the house to add (House obj)
     * @return boolean - true if successful
     */
    public Boolean addHouse(House house){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("houseName", house.getHouseName());
        values.put("long", house.getLongitude());
        values.put("lat", house.getLatitude());
        values.put("headOfHouseID", house.getHeadOfHouseID());


        long result = db.insert("house", null, values);

        for(int i = 0; i < house.getMembers().size(); i++){
            addUserToHouse(house.getMembers().get(i).getID(), ((int)result));
        }

        if (result > 0) {
            Log.v("dbhelper", "inserted successfully");
            return true;
        } else {
            Log.v("dbhelper", "failed to insert");
            return false;
        }
    }


    /**
     * Update task
     * @param task - task Obj to update
     * @return true if update
     */
    public Boolean updateTask(taskObj task){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", task.getMadeBy().getID());
        values.put("title", task.getTitle());
        if(task.isCompleted()){
            values.put("completed", 1);
        }else{
            values.put("completed", 0);
        }
        values.put("datemade", task.getStringDateMade());
        values.put("timemade", task.getStringTimeMade());

        long result = db.update("houseMembers", values, "_id=", new String[]{task.getID().toString()});

        if (result > 0) {
            Log.v("dbhelper", "inserted successfully");
            return true;
        } else {
            Log.v("dbhelper", "failed to insert");
            return false;
        }
    }

    /**
     * Add task
     * @param task - task Obj to add
     * @param houseID - houseID of house to add task to
     * @return true if inserted
     */
    public Boolean addTask(taskObj task, int houseID){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", task.getMadeBy().getID());
        values.put("houseID", houseID);
        values.put("title", task.getTitle());
        if(task.isCompleted()){
            values.put("completed", 1);
        }else{
            values.put("completed", 0);
        }
        values.put("datemade", task.getStringDateMade());
        values.put("timemade", task.getStringTimeMade());

        long result = db.insert("houseMembers", null, values);

        if (result > 0) {
            Log.v("dbhelper", "inserted successfully");
            return true;
        } else {
            Log.v("dbhelper", "failed to insert");
            return false;
        }
    }

    /**
     * Add user to a household
     * @param userID - userID of user to add to house
     * @param houseID - houseID of house to add user to
     * @return true if inserted
     */
    public Boolean addUserToHouse(Integer userID, Integer houseID){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("userID", userID);
        values.put("houseID", houseID);


        long result = db.insert("houseMembers", null, values);

        if (result > 0) {
            Log.v("dbhelper", "inserted successfully");
            return true;
        } else {
            Log.v("dbhelper", "failed to insert");
            return false;
        }

    }

    /**
     * Gets all houses
     * @param houseID houseID of which to get details upon
     * @return ArrayList of houses
     */
    public House getHouseOne(Integer houseID){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor c = db.rawQuery("select * from house where _id = ? ", new String[] {houseID.toString()});

        House house = new House();
        if (c.moveToFirst()) {
            do {

                house.setID(c.getInt(c.getColumnIndex("_id")));
                house.setHeadOfHouseID(c.getInt(c.getColumnIndex("headOfHouseID")));
                house.setHouseName(c.getString(c.getColumnIndex("houseName")));
                house.setLatitude(c.getDouble(c.getColumnIndex("lat")));
                house.setLongitude(c.getDouble(c.getColumnIndex("long")));

                house.setTasks(this.getTasks(house.getID()));
                house.setMembers(this.getMembers(house.getID()));
            } while (c.moveToNext());
        }

        return house;
    }

    /**
     * Checks if user is already a member of a house
     * @param userID - user being added
     * @param houseID - house to check by
     * @return boolean - true if already member
     */
    private Boolean isAlreadyMemeber(Integer userID, Integer houseID){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor c = db.rawQuery("select count(_id) from houseMembers where userID = ? and houseID = ? ", new String[] {userID.toString(), houseID.toString()});

        c.moveToFirst();

        Integer count = c.getInt(0);
        if(count != 0){
            return true;
        }
        c.close();
        return false;
    }


    /**
     * Gets member IDs for a house ID
     * @param userID - user to get house list based on
     * @return ArrayList of IDs
     */
    private ArrayList<Integer> getHouseIDList(Integer userID){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor c = db.rawQuery("select * from houseMembers where userID = ? ", new String[] {userID.toString()});


        ArrayList<Integer> houses = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
            Integer ID = c.getInt(c.getColumnIndex("_id"));

            houses.add(c.getInt(c.getColumnIndex("houseID")));
            c.moveToNext();
            } while (c.moveToNext());
        }
        c.close();
        return houses;
    }

    /**
     * Gets house details based on a userID
     * @param userID - userID to get houses based on
     * @return ArrayList of houses
     */
    public ArrayList<House> getHouses(Integer userID){
        ArrayList<Integer> houseIDs = getHouseIDList(userID);
        ArrayList<House> houses = new ArrayList<>();
        for(int i = 0; i < houseIDs.size(); i++){
            houses.add(this.getHouseOne(houseIDs.get(i)));
        }

        return houses;
    }


    /**
     * Gets member IDs for a house ID
     * @param houseID - house to get member list based on
     * @return ArrayList of IDs
     */
    private ArrayList<Integer> getMemberIDList(Integer houseID){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("select * from houseMembers where houseID = ? ", new String[] {houseID.toString()});

        c.moveToFirst();
        ArrayList<Integer> members = new ArrayList<>();
        while (!c.isAfterLast()) {
            members.add(c.getInt(c.getColumnIndex("userID")));
            c.moveToNext();
        }
        c.close();
        return members;
    }

    /**
     * Gets member details based on a houseID
     * @param houseID - house ID to get members based on
     * @return ArrayList of members
     */
    public ArrayList<User> getMembers(Integer houseID){
        ArrayList<Integer> memberIDs = getMemberIDList(houseID);

        ArrayList<User> members = new ArrayList<>();
        for(int i = 0; i < memberIDs.size(); i++){
            Log.v("getMembers", memberIDs.get(i).toString());
            members.add(this.getUser(memberIDs.get(i)));
        }
        return members;
    }

    /**
     * Gets the tasks associated to a house
     * @param houseID - house to get tasks based on
     * @return ArrayList of tasks
     */
    public ArrayList<taskObj> getTasks(Integer houseID){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor c = db.rawQuery("select * from task where houseID = ? ", new String[] {houseID.toString()});

        c.moveToFirst();
        ArrayList<taskObj> tasks = new ArrayList<>();
        while (!c.isAfterLast()) {
            Integer ID = c.getInt(c.getColumnIndex("_id"));

            taskObj task = new taskObj();
            task.setDateMadeString(c.getString(c.getColumnIndex("datemade")));
            task.setTimeMadeString(c.getString(c.getColumnIndex("timemade")));
            int completed = c.getInt(c.getColumnIndex("completed"));
            boolean com;
            if(completed == 0) {
                com = false;
            }else{
                com = true;
            }
            task.setCompleted(com);
            task.setTitle(c.getString(c.getColumnIndex("title")));
            task.setID(c.getInt(c.getColumnIndex("_id")));
            task.setMadeBy(getUser(c.getInt(c.getColumnIndex("userID"))));
            tasks.add(task);
            c.moveToNext();
        }
        c.close();
        return tasks;
    }

    /**
     * Checks if there is a user with the same email in the database
     * @param email - the email to check the user by
     * @return true if there is a duplicate user
     */
    public Boolean checkDuplicateUser(String email) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        Cursor c = db.rawQuery("select count(_id) from user where email = ? ", new String[] {email});

        c.moveToFirst();

        Integer count = c.getInt(0);
        if(count != 0){
            return true;
        }
        c.close();
        return false;
    }

    /**
     * Gets user details corrosponding to the ID given
     * @param ID - ID to get details from
     * @return User obj with user's details
     */
    public User getUser(Integer ID){
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();
        Cursor c = db.rawQuery("SELECT * FROM user WHERE _id = ? ", new String[] {ID.toString()});
        if(c.getCount()<1){
            c.close();
            return null;
        }
        if (c.moveToLast()){

            // Passing values
            user.setID(c.getInt(c.getColumnIndex("_id")));
            user.setFirstName(c.getString(c.getColumnIndex("firstName")));
            user.setLastName(c.getString(c.getColumnIndex("lastName")));
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setImg(getBitmapFromBytes(c.getBlob(c.getColumnIndex("img"))));

        }
        if(user.getID() == null){
            return null;
        }
        c.close();
        return user;
    }

    /**
     * Gets user details corrosponding to the email given
     * @param email - email to get details from
     * @return User obj with user's details
     */
    public User getUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();
        Cursor c = db.rawQuery("SELECT * FROM user WHERE email = ? ", new String[] {email});
        if(c.getCount()<1){
            c.close();
            return null;
        }
        if (c.moveToLast()){

            // Passing values
            user.setID(c.getInt(c.getColumnIndex("_id")));
            user.setFirstName(c.getString(c.getColumnIndex("firstName")));
            user.setLastName(c.getString(c.getColumnIndex("lastName")));
            user.setEmail(c.getString(c.getColumnIndex("email")));
            user.setImg(getBitmapFromBytes(c.getBlob(c.getColumnIndex("img"))));

        }
        if(user.getID() == null){
            return null;
        }
        c.close();
        return user;
    }

    /**
     * Gets all of the users
     * @return ArrayList of users
     */
    public ArrayList<User> getUsers(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM user",null);
        ArrayList<User> usersList = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Integer ID = c.getInt(c.getColumnIndex("_id"));
                if (ID == null) {
                    return usersList;
                }
                User user = new User();
                // Passing values
                user.setID(c.getInt(c.getColumnIndex("_id")));
                user.setFirstName(c.getString(c.getColumnIndex("firstName")));

                user.setLastName(c.getString(c.getColumnIndex("lastName")));
                user.setEmail(c.getString(c.getColumnIndex("email")));
                user.setImg(getBitmapFromBytes(c.getBlob(c.getColumnIndex("img"))));
                Log.v("db-users", user.getEmail());
                usersList.add(user);
            }while(c.moveToNext());
        }

        c.close();
        return usersList;
    }

    /**
     * Checks if user exists, if they do updates their data
     * @param user user Obj to get information
     * @return true if updated
     */
    public Boolean updateUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        if(!this.checkDuplicateUser(user.getEmail())){//User doesn't exist
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        if(user.getImg() != null){
            Bitmap img = user.getImg();
            byte[] imgByte = getBytesFromBitmap(img);

            values.put("img", imgByte);
        }

        long result = db.update("user", values,"_id = ?", new String[]{user.getID().toString()});
        Toast.makeText(dbcontext, Long.toString(result), Toast.LENGTH_SHORT).show();


        if (result > 0) {
            Log.v("dbhelper", "inserted successfully");
            return true;
        } else {
            Log.v("dbhelper", "failed to insert");
            return false;
        }
    }

    /**
     * Checks if user exists, if doesn't, adds them to database
     * @param user user Obj to get information
     * @return true if inserted
     */
    public Boolean addUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        if(this.checkDuplicateUser(user.getEmail())){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        if(user.getImg() != null){
            Bitmap img = user.getImg();
            byte[] imgByte = getBytesFromBitmap(img);

            values.put("img", imgByte);
        }

        long result = db.insert("user", null, values);

        if (result > 0) {
            Log.v("dbhelper", "inserted successfully");
            return true;
        } else {
            Log.v("dbhelper", "failed to insert");
            return false;
        }


    }

    /**
     * Converts bitmap to bytes (blob)
     * @param bitmap - bitmap to convert
     * @return bytes[]
     */
    private static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    /**
     * Converts bytes[] (blob) to bitmap
     * @param bytes the byte[]
     * @return bitmap
     */
    private static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
    }
}


