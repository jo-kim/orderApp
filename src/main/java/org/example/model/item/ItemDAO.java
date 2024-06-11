package org.example.model.item;

import org.example.util.Jdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    private static Logger log = LoggerFactory.getLogger(ItemDAO.class);

    private static final String ITEM_LIST = "SELECT * FROM ITEM";
    private static final String GET_ITEM = "SELECT * FROM ITEM WHERE ITEM_NO = ?";
    private static final String UPDATE_ITEM = "UPDATE ITEM SET QUANTITY = ? WHERE ITEM_NO = ?";
    public List<Item> getItemList() {
        List<Item> itemList = new ArrayList<>();
        try{
            conn = Jdbc.getConnection();
            pstmt = conn.prepareStatement(ITEM_LIST);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Item item = new Item(
                        rs.getLong("ITEM_NO"),
                        rs.getString("ITEM_NAME"),
                        rs.getInt("PRICE"),
                        rs.getInt("QUANTITY")
                );
                itemList.add(item);
            }
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }finally {
            Jdbc.close(rs, pstmt, conn);
        }
        return itemList;
    }

    public Item getItem(final long itemNo) {
        Item item = null;
        try{
            conn = Jdbc.getConnection();
            pstmt = conn.prepareStatement(GET_ITEM);
            pstmt.setLong(1, itemNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                item =  new Item(rs.getLong("ITEM_NO"),
                        rs.getString("ITEM_NAME"),
                        rs.getInt("PRICE"),
                        rs.getInt("QUANTITY"));
            }
        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }
        return item;
    }

    public void updateItem(final Item item) {
        try {
            conn = Jdbc.getConnection();
            pstmt = conn.prepareStatement(UPDATE_ITEM);
            pstmt.setInt(1, item.getQuantity());
            pstmt.setLong(2, item.getItemNo());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            log.error("Error : {}", e.getMessage());
        }finally {
            Jdbc.close(pstmt, conn);
        }
    }
}
