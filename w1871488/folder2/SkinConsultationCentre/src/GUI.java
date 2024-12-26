import javax.crypto.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static javax.crypto.Cipher.getInstance;

public class GUI extends WestminsterSkinConsultationManager implements ActionListener {
    ArrayList<Consultation> consultations = new ArrayList<>();
    private static BufferedImage globalImage;

    //Array for encrypted image store
    byte[] encryptedImageData;

    //Encryption key
    SecretKey key;

    //Creating GUI Interface
    public GUI()  {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        try{
            BufferedImage backgroundImage = ImageIO.read(new File("R.jpg"));
            int width = 400;
            int height = 100;
            Image scaledImage = backgroundImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaledImage));
            panel.add(label);
        }catch (Exception ignored){

        }

        panel.setBackground(new Color(1200000));

        JButton viewDoctors = new JButton("View the List of Doctors");
        JButton sortDoctors = new JButton("Sort the Doctor List Alphabetically");
        JButton consultButton = new JButton("Add a Consultation");
        JButton showConsultation = new JButton("Show Consultations");

        viewDoctors.setBackground(Color.LIGHT_GRAY);
        sortDoctors.setBackground(Color.LIGHT_GRAY);
        consultButton.setBackground(Color.LIGHT_GRAY);
        showConsultation.setBackground(Color.LIGHT_GRAY);

        consultButton.addActionListener(e -> addConsultation());


        showConsultation.addActionListener(e -> showConsultations());


        viewDoctors.addActionListener(this);

        sortDoctors.addActionListener(e -> sortedDoctorList());

        panel.setBorder(BorderFactory.createEmptyBorder(120, 170, 170, 170));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(viewDoctors);
        panel.add(sortDoctors);
        panel.add(consultButton);
        panel.add(showConsultation);
        panel.setPreferredSize(new Dimension(700,800));
        frame.add(panel, BorderLayout.PAGE_START);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Skin Consultation Center System");
        frame.setSize(800, 800);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doctorListWindow();
    }

    public void doctorListWindow() {


        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("First Name");
        model.addColumn("Sur Name");
        model.addColumn("Date of Birth");
        model.addColumn("Mobile Number");
        model.addColumn("Specialisation");
        model.addColumn("Medical License Number");


        for (Doctor doctor : doctorList) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSurname(), doctor.getDateOfBirth(), doctor.getMobileNumber(), doctor.getSpecialisation(), doctor.getMedicalLicenceNumber()});
        }

        JTable table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        JFrame frame1 = new JFrame("Doctor List");

        table.setEnabled(false);
        frame1.add(scroll, BorderLayout.CENTER);
        frame1.setSize(900, 700);
        frame1.setVisible(true);


    }
    //Sorted the doctor list

    public void sortedDoctorList() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("First Name");
        model.addColumn("Sur Name");
        model.addColumn("Date of Birth");
        model.addColumn("Mobile Number");
        model.addColumn("Specialisation");
        model.addColumn("Medical License Number");
        Collections.sort(doctorList);
        for (Doctor doctor : doctorList) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSurname(), doctor.getDateOfBirth(), doctor.getMobileNumber(), doctor.getSpecialisation(), doctor.getMedicalLicenceNumber()});
        }

        JTable table = new JTable(model);

        table.getSelectedColumn();

        JScrollPane scroll = new JScrollPane(table);

        JFrame frame2 = new JFrame("Sorted Doctor List");
        table.setEnabled(false);
        frame2.add(scroll, BorderLayout.CENTER);
        frame2.setSize(900, 700);
        frame2.setVisible(true);
    }

    public void addConsultation() {

        // create the dropdown menu
        JComboBox<String> dropdown = new JComboBox<>();

        for (Doctor doctor : doctorList) {
            dropdown.addItem(doctor.getName() + " " + doctor.getSurname());
        }

        // create the button
        JButton button = new JButton("Consult");

        // create the label
        JLabel label = new JLabel("Please select a Doctor: ");

        // create a panel
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(dropdown);
        panel.add(button);

        // create the frame and add the panel
        JFrame frame3 = new JFrame("Add Consultation");
        frame3.add(panel);
        frame3.pack();
        frame3.setVisible(true);

        //Method to close only the frame after consultation
        frame3.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Close the frame
                frame3.dispose();
            }
        });



        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame jFrame1 = new JFrame("Enter Appointment Details");
                JPanel JPanel1 = new JPanel(new FlowLayout(FlowLayout.TRAILING));

                Date inDate = new Date();
                Date minDate = new Date(0);
                Date maxDate = new Date(Long.MAX_VALUE);
                SpinnerDateModel dateModel = new SpinnerDateModel(inDate, minDate, maxDate, Calendar.MINUTE);

                // Create a JSpinner with the SpinnerDateModel
                JSpinner spinner1 = new JSpinner(dateModel);

                JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner1, "dd-MM-yyyy HH:mm");
                spinner1.setEditor(editor);

                // Create a SpinnerNumberModel
                SpinnerNumberModel sNumberModel = new SpinnerNumberModel(0.0, 0.0, null, 0.1);

                // Create a JSpinner with the SpinnerNumberModel
                JSpinner costSpinner = new JSpinner(sNumberModel);

                JTextField jNoteField = new JTextField();


                JLabel label_1 = new JLabel("Select the Date & Time: ");
                JLabel label_2 = new JLabel("Select the Cost (£): ");
                JLabel label_3 = new JLabel("Notes: ");
                JButton button_1 = new JButton("Submit");
                button_1.setEnabled(false);
                JLabel label_8 = new JLabel("Patient Id: ");
                JTextField patientIdField = new JTextField();
                JLabel label_4 = new JLabel("Patients Name: ");
                JTextField nameField = new JTextField();
                JLabel label_5 = new JLabel("Patients Surname: ");
                JTextField surnameField = new JTextField();
                JLabel label_6 = new JLabel("Patients Date of Birth: ");
                JTextField dobField = new JTextField();
                JLabel label_7 = new JLabel("Patients Mobile Number: ");
                JTextField numberField = new JTextField();


                ((AbstractDocument) numberField.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                            throws BadLocationException {
                        // Only allow integer input
                        if (string.matches("^\\d+$")) {
                            super.insertString(fb, offset, string, attr);
                        }
                    }

                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                            throws BadLocationException {
                        // Only allow integer input
                        if (text.matches("^\\d+$")) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });

                ((AbstractDocument) patientIdField.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                            throws BadLocationException {
                        // Only allow integer input
                        if (string.matches("^\\d+$")) {
                            super.insertString(fb, offset, string, attr);
                        }
                    }

                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                            throws BadLocationException {
                        // Only allow integer input
                        if (text.matches("^\\d+$")) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });


                jFrame1.setPreferredSize(new Dimension(1250, 750));
                JPanel1.setLayout(new BoxLayout(JPanel1, BoxLayout.Y_AXIS));

                JButton uploadButton = new JButton("Upload Images");
                uploadButton.addActionListener(e1 -> {
                    JFileChooser chooser = new JFileChooser();
                    int result = chooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        try {
                            globalImage = ImageIO.read(selectedFile);
                            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                            ImageIO.write(globalImage, "jpg", byteArray);
                            byteArray.flush();
                            byte[] imageData = byteArray.toByteArray();
                            byteArray.close();
                            Cipher cipher1 = getInstance("AES/ECB/PCS5Padding");
                            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                            keyGenerator.init(128); // set key size to 128 bits
                            key = keyGenerator.generateKey();

                            cipher1.init(Cipher.ENCRYPT_MODE, key);
                            encryptedImageData = cipher1.doFinal(imageData);

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (NoSuchPaddingException ex) {
                            throw new RuntimeException(ex);
                        } catch (IllegalBlockSizeException ex) {
                            throw new RuntimeException(ex);
                        } catch (NoSuchAlgorithmException ex) {
                            throw new RuntimeException(ex);
                        } catch (BadPaddingException ex) {
                            throw new RuntimeException(ex);
                        } catch (InvalidKeyException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });


                JPanel1.add(label_1);
                JPanel1.add(spinner1);
                JPanel1.add(label_2);
                JPanel1.add(costSpinner);
                JPanel1.add(label_3);
                JPanel1.add(jNoteField);
                JPanel1.add(uploadButton, BorderLayout.EAST);
                JPanel1.add(new JSeparator(SwingConstants.HORIZONTAL));
                jFrame1.add(JPanel1);
                JPanel1.add(label_4);
                JPanel1.add(nameField);
                JPanel1.add(label_5);
                JPanel1.add(surnameField);
                JPanel1.add(label_6);
                JPanel1.add(dobField);
                JPanel1.add(label_7);
                JPanel1.add(numberField);
                JPanel1.add(label_8);
                JPanel1.add(patientIdField);

                JPanel1.add(button_1);
                jFrame1.pack();
                jFrame1.setVisible(true);

                jFrame1.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Close the frame
                        jFrame1.dispose();
                    }
                });



                DocumentListener listener = new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        checkFields();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        checkFields();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                    }

                    private void checkFields() {
                        button_1.setEnabled(!nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !dobField.getText().isEmpty() && !numberField.getText().isEmpty() && !patientIdField.getText().isEmpty());
                    }
                };


                // Attach the DocumentListener to the text fields
                nameField.getDocument().addDocumentListener(listener);
                surnameField.getDocument().addDocumentListener(listener);
                dobField.getDocument().addDocumentListener(listener);
                numberField.getDocument().addDocumentListener(listener);
                patientIdField.getDocument().addDocumentListener(listener);


                button_1.addActionListener(e2 -> {
                    Date selectedDate = (Date) spinner1.getValue();
                    double selectedValue = ((Number) costSpinner.getValue()).doubleValue();
                    String notes = jNoteField.getText();



                    for (int i = 0; i < doctorList.size(); i++) {
                        if (Objects.requireNonNull(dropdown.getSelectedItem()).toString().contains(doctorList.get(i).getName())) {
                            if ( consultations.isEmpty()) {
                                Consultation consult1 = new Consultation();
                                consult1.setCost(selectedValue);
                                consult1.setNotes(notes);
                                consult1.setDoctor(doctorList.get(i));
                                consult1.setName(nameField.getText());
                                consult1.setSurname(surnameField.getText());
                                consult1.setDateOfBirth(dobField.getText());
                                consult1.setMobileNumber(Integer.parseInt(numberField.getText()));
                                consult1.setPatientId(Integer.parseInt(patientIdField.getText()));
                                consult1.setConsultationDateAndTime(selectedDate);
                                try{
                                    consult1.setEncryptedImageArray(encryptedImageData);
                                    encryptedImageData = null;
                                    consultations.add(consult1);
                                    JOptionPane.showMessageDialog(null, "Consultation Added with Doctor " + doctorList.get(i).getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                    jFrame1.dispose();
                                }catch (Exception n){
                                    consultations.add(consult1);
                                    JOptionPane.showMessageDialog(null, "Consultation Added with Doctor " + doctorList.get(i).getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                    jFrame1.dispose();
                                }
                            }
                            else if (!(consultations.get(i).getConsultationDateAndTime().equals(selectedDate))){
                                Consultation consult1 = new Consultation();
                                consult1.setCost(selectedValue);
                                consult1.setNotes(notes);
                                consult1.setDoctor(doctorList.get(i));
                                consult1.setName(nameField.getText());
                                consult1.setSurname(surnameField.getText());
                                consult1.setDateOfBirth(dobField.getText());
                                consult1.setMobileNumber(Integer.parseInt(numberField.getText()));
                                consult1.setPatientId(Integer.parseInt(patientIdField.getText()));
                                consult1.setConsultationDateAndTime(selectedDate);
                                consult1.setEncryptedImageArray(encryptedImageData);
                                encryptedImageData = null;
                                consultations.add(consult1);
                                JOptionPane.showMessageDialog(null, "Consultation Added with Doctor " + doctorList.get(i).getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                jFrame1.dispose();
                            }

                            else {

                                Random rng = new Random();
                                ArrayList<Doctor> excludedDoctorList = (ArrayList<Doctor>) doctorList.clone();
                                excludedDoctorList.remove(i);
                                if (excludedDoctorList.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Doctor " + doctorList.get(i).getName() + " isn't available at the mentioned time, unable to find any other Doctors at this time", "Sorry", JOptionPane.INFORMATION_MESSAGE);
                                    jFrame1.dispose();
                                } else {
                                    int index = rng.nextInt(excludedDoctorList.size());
                                    Doctor newDoctor = excludedDoctorList.get(index);
                                    JOptionPane.showMessageDialog(null, "Doctor " + doctorList.get(i).getName() + " is not available at that time, consultation will be scheduled with Doctor " + newDoctor.getName(), "Alert", JOptionPane.INFORMATION_MESSAGE);
                                    Consultation consult1 = new Consultation();
                                    consult1.setCost(selectedValue);
                                    consult1.setNotes(notes);
                                    consult1.setDoctor(newDoctor);
                                    consult1.setName(nameField.getText());
                                    consult1.setSurname(surnameField.getText());
                                    consult1.setDateOfBirth(dobField.getText());
                                    consult1.setMobileNumber(Integer.parseInt(numberField.getText()));
                                    consult1.setPatientId(Integer.parseInt(patientIdField.getText()));
                                    consult1.setConsultationDateAndTime(selectedDate);
                                    consult1.setEncryptedImageArray(encryptedImageData);
                                    encryptedImageData = null;
                                    consultations.add(consult1);
                                    jFrame1.dispose();
                                }
                            }
                        }
                    }
                });
            }
        });
    }
    public void showConsultations() {

        JFrame fM = new JFrame("Consultations");
        JPanel pl = new JPanel();

        for (int a=0;a<consultations.size();a++){

            JButton button1 = new JButton(consultations.get(a).getName()+" - "+consultations.get(a).getConsultationDateAndTime());

            int finalize = a;
            button1.addActionListener(e -> {

                JFrame jFrame1 = new JFrame("Details of "+consultations.get(finalize).getName()+"'s Appointment");
                JPanel jPanel1 = new JPanel();

                JLabel label_3 = new JLabel("Doctor's Name: ");
                JTextArea textArea0 = new JTextArea(consultations.get(finalize).getDoctor().getName()+" "+consultations.get(finalize).getDoctor().getSurname());
                textArea0.setEditable(false);
                JLabel label_4 = new JLabel("Patient's Name: ");
                JTextArea textArea1 = new JTextArea(consultations.get(finalize).getName());
                textArea1.setEditable(false);
                JLabel label_5 = new JLabel("Patient's Surname: ");
                JTextArea textArea2 = new JTextArea(consultations.get(finalize).getSurname());
                textArea2.setEditable(false);
                JLabel label_6 = new JLabel("Patient's Date of Birth: ");
                JTextArea textArea3 = new JTextArea(consultations.get(finalize).getDateOfBirth());
                textArea3.setEditable(false);
                JLabel label_7 = new JLabel("Patient's Mobile Number: ");
                JTextArea textArea4 = new JTextArea(String.valueOf(consultations.get(finalize).getMobileNumber()));
                textArea4.setEditable(false);
                JLabel label_8 = new JLabel("Patient Id: ");
                JTextArea textArea5 = new JTextArea(String.valueOf(consultations.get(finalize).getPatientId()));
                textArea5.setEditable(false);
                JLabel label_9 = new JLabel("Scheduled Date and Time: ");
                JTextArea textArea6 = new JTextArea(String.valueOf(consultations.get(finalize).getConsultationDateAndTime()));
                textArea6.setEditable(false);
                JLabel label_10 = new JLabel("Appointment Cost: ");
                JTextArea textArea7 = new JTextArea(String.valueOf(consultations.get(finalize).getCost()));
                textArea7.setEditable(false);
                JLabel label_11 = new JLabel("Appointment Notes: ");
                JTextArea textArea8 = new JTextArea(consultations.get(finalize).getNotes());
                textArea8.setEditable(false);

                try{
                    if (consultations.get(finalize).getEncryptedImageArray() != null){
                        JButton showImage = new JButton("Show Images");
                        jPanel1.add(showImage);
                        showImage.addActionListener(e1 -> {
                            JFrame f1 = new JFrame();
                            JPanel p1 = new JPanel();
                            f1.add(p1);
                            int width = 650;
                            int height = 650;

                            Cipher cipher;
                            try {
                                cipher = getInstance("AES/ECB/PCS5Padding");
                            } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                cipher.init(Cipher.DECRYPT_MODE, key);
                            } catch (InvalidKeyException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                byte[] decryptedImageData = cipher.doFinal(consultations.get(finalize).getEncryptedImageArray());
                                InputStream in = new ByteArrayInputStream(decryptedImageData);
                                BufferedImage image = ImageIO.read(in);

                                // resize the image
                                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g = resizedImage.createGraphics();
                                g.drawImage(image, 0, 0, width, height, null);
                                g.dispose();

                                // create the JLabel with the resized image
                                JLabel label = new JLabel(new ImageIcon(resizedImage));
                                label.setPreferredSize(new Dimension(width, height));
                                ImageIcon imageIcon = new ImageIcon(resizedImage);
                                label.setIcon(imageIcon);

                                p1.add(label);
                                f1.pack();
                                f1.setVisible(true);
                            } catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        });
                    }
                }catch (Exception ignored){

                }

                jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));
                jFrame1.add(jPanel1);
                jPanel1.add(label_3);
                jPanel1.add(textArea0);
                jPanel1.add(label_4);
                jPanel1.add(textArea1);
                jPanel1.add(label_5);
                jPanel1.add(textArea2);
                jPanel1.add(label_6);
                jPanel1.add(textArea3);
                jPanel1.add(label_7);
                jPanel1.add(textArea4);
                jPanel1.add(label_8);
                jPanel1.add(textArea5);
                jPanel1.add(label_9);
                jPanel1.add(textArea6);
                jPanel1.add(label_10);
                jPanel1.add(textArea7);
                jPanel1.add(label_11);
                jPanel1.add(textArea8);

                jFrame1.pack();
                jFrame1.setVisible(true);

            });

            pl.setPreferredSize(new Dimension(650,450));
            pl.add(button1);
        }
        fM.setPreferredSize(new Dimension(650, 450));
        fM.add(pl);
        fM.pack();
        fM.setVisible(true);
    }
}