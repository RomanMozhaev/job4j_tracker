package ru.job4j.trackersql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tracker.ITracker;
import ru.job4j.tracker.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TrackerSQL implements ITracker {

    private static final Logger LOG = LoggerFactory.getLogger(TrackerSQL.class.getName());
    private Connection connection;

    public TrackerSQL() {
        init();
    }

    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }

    /**
     * THe method initialises the connection to the data base.
     *
     * @return true if connected
     */
    public boolean init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            Statement st = connection.createStatement();
            String table = "CREATE TABLE IF NOT EXISTS items (item_id serial primary key, item_name varchar(100), item_desc varchar(500), item_time timestamp);";
            st.executeUpdate(table);
            st.close();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return this.connection != null;
    }

    /**
     * the method add the line into the table items.
     *
     * @param item - the line for adding
     * @return item if adding is finished successfully or null.
     */
    @Override
    public Item add(Item item) {
        Item result = null;
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO items (item_desc, item_name, item_time) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getDecs());
            st.setString(2, item.getName());
            st.setTimestamp(3, new Timestamp(item.getTime()));
            st.executeUpdate();
            ResultSet generatedKey = st.getGeneratedKeys();
            if (generatedKey.next()) {
                result = item;
                String key = Long.toString(generatedKey.getLong(1));
                result.setId(key);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * The method replaces the information in the row.
     *
     * @param id   - row id
     * @param item - new row
     * @return - true if the row was updated
     */
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        try (PreparedStatement st = connection.prepareStatement("UPDATE items SET (item_desc, item_name, item_time) = (?, ?, ?) WHERE item_id = ?")) {
            st.setString(1, item.getDecs());
            st.setString(2, item.getName());
            st.setTimestamp(3, new Timestamp(item.getTime()));
            st.setInt(4, Integer.parseInt(id));
            int rows = st.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * the method deletes the row with id
     *
     * @param id - id of the row for deleting
     * @return - true if deleted
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        try (PreparedStatement st = connection.prepareStatement("DELETE FROM items WHERE item_id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            int rows = st.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * the method deletes all rows
     *
     * @return true if deleted at least one row.
     */
    public boolean deleteAll() {
        boolean result = false;
        try (PreparedStatement st = connection.prepareStatement("DELETE FROM items")) {
            int rows = st.executeUpdate();
            if (rows > 0) {
                result = true;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * the method returns the array list with all items/rows
     *
     * @return - the list
     */
    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM items");
            while (resultSet.next()) {
                int id = resultSet.getInt("item_id");
                String name = resultSet.getString("item_name");
                String desc = resultSet.getString("item_desc");
                Timestamp time = resultSet.getTimestamp("item_time");
                Item item = new Item(name, desc, time.getTime());
                item.setId(String.valueOf(id));
                result.add(item);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * the method returns all rows which contains the key in the name.
     *
     * @param key - the key for searching
     * @return - the array list with items/rows
     */
    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement("SELECT * FROM items WHERE item_name LIKE ?")) {
            st.setString(1, "%" + key + "%");
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("item_id");
                String name = resultSet.getString("item_name");
                String desc = resultSet.getString("item_desc");
                Timestamp time = resultSet.getTimestamp("item_time");
                Item item = new Item(name, desc, time.getTime());
                item.setId(String.valueOf(id));
                result.add(item);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * the method returns the item/row with corresponded id.
     *
     * @param id - the id for searching
     * @return item if it is found or null
     */
    @Override
    public Item findById(String id) {
        Item result = null;
        try (PreparedStatement st = connection.prepareStatement("SELECT * FROM items WHERE item_id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                int itemId = resultSet.getInt("item_id");
                String name = resultSet.getString("item_name");
                String desc = resultSet.getString("item_desc");
                Timestamp time = resultSet.getTimestamp("item_time");
                result = new Item(name, desc, time.getTime());
                result.setId(String.valueOf(itemId));
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}