package com.app.coding.ui;

import com.app.coding.db.DbService;
import com.app.coding.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements ActionListener {

    // Тут буду загружать данные которые мы будем видеть в таблице
    List<String[]> data = new ArrayList<>();
    // Шапочка таблицы
    String[] columnNames = {"ID", "First Name", "Last Name", "Email"};

    // В конструкторе определяем что изначально мы увидим когда отрисуем этот елемент
    public UserManager() {
        // Достаю модель таблицы
        DefaultTableModel model = (DefaultTableModel) allUsersTable.getModel();
        // Делаю чистку таблицы на всякий
        model.setRowCount(0);
        // Заполняем шапочку таблицы
        for (String c : columnNames) {
            model.addColumn(c);
        }
        // Стили таблицы - горизонтальные линии
        allUsersTable.setShowHorizontalLines(true);
        // Выставляем видимость елеиментов
        createUserPanel.setVisible(false);
        newUserButton.addActionListener(this);
        refreshButton.addActionListener(this);
        createButton.addActionListener(this);
        removeSelectedRowButton.addActionListener(this);
    }

    private JPanel jPanel;
    private JTable allUsersTable;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton createButton;
    private JButton refreshButton;
    private JPanel createUserPanel;
    private JButton newUserButton;
    private JPanel tablePanel;
    private JButton removeSelectedRowButton;

    // Тут приходят события с нажатия кнопок
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("some event -> " + e.getActionCommand());
        if (e.getActionCommand().equals("Create")) {
            createUserPanel.setVisible(false);
            tablePanel.setVisible(true);
            User user = new User();
            user.setFirstName(firstNameField.getText());
            user.setLastName(lastNameField.getText());
            user.setEmail(emailField.getText());
            DbService.createNewUser(user);
            refreshAllData();
            newUserButton.setVisible(true);
            refreshButton.setVisible(true);
            removeSelectedRowButton.setVisible(true);
        } else if (e.getActionCommand().equals("Refresh")){
            refreshAllData();
        } else if (e.getActionCommand().equals("New User")){
            newUserButton.setVisible(false);
            refreshButton.setVisible(false);
            removeSelectedRowButton.setVisible(false);
            createUserPanel.setVisible(true);
            tablePanel.setVisible(false);
        } else if (e.getActionCommand().equals("Remove Selected Row")){
            DefaultTableModel model = (DefaultTableModel) allUsersTable.getModel();
            int selectedRowIndex = allUsersTable.getSelectedRow();
            if (selectedRowIndex > -1){
                model.removeRow(selectedRowIndex);
                String id = data.get(selectedRowIndex)[0];
                DbService.removeUserById(id);
                refreshAllData();
            }
        }
    }


    // Чистим таблицу и заполняем ее по новой данными из базы
    private void refreshAllData() {
        data.clear();
        DbService.getAllUsers().forEach(user
                -> data.add(new String[]{user.getId().toString(), user.getFirstName(), user.getLastName(), user.getEmail()}));
        DefaultTableModel model = (DefaultTableModel) allUsersTable.getModel();
        model.setRowCount(0);
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    public JPanel getjPanel() {
        return jPanel;
    }
}
