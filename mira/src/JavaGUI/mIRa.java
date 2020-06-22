/**
 * @author Harsh Gupta, Amrit Pandher, and Navroop Singh
 *
 */

package JavaGUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import mira.Condition;
import mira.Drug;
import mira.Parse;
import mira.Review;
import search.Trie;

public class mIRa {

	private JFrame frame;
	private JTextField txtEnterTheDrugs;
	private JTabbedPane tabbedPane;
	private JLabel lblNewLabel;
	private static Trie t;
	private static Map<String, Condition> l;
	private JScrollPane scrollPane;
	private JPanel reviewPane;
	private JScrollPane scrollPane_1;
	private JPanel drugPane;
	private JScrollPane scrollPane_2;
	private JPanel conditionPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					t = new Trie();
					l = Parse.loadData();
					for (Map.Entry<String, Condition> entry : l.entrySet()) {
//		                System.out.println(entry.getKey() + "/" + entry.getValue());
		                t.insert(entry.getKey());
		            }
//					List<String> a= t.autocomplete("B");
					mIRa window = new mIRa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mIRa() {
		initialize();
	}

	/**
	 * @param sentence takes in a sentence and splits it into seperate words
	 * @return A seperated sentence
	 */
	public static String upperCaseWords(String sentence) {
        String words[] = sentence.replaceAll("\\s+", " ").trim().split(" ");
        String newSentence = "";
        for (String word : words) {
            for (int i = 0; i < word.length(); i++)
                newSentence = newSentence + ((i == 0) ? word.substring(i, i + 1).toUpperCase(): 
                    (i != word.length() - 1) ? word.substring(i, i + 1).toLowerCase() : word.substring(i, i + 1).toLowerCase().toLowerCase() + " ");
        }

        return newSentence;
    }
	
	
	/**
	 * Start the frame and place contents in the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(246, 241, 208)); //setting color
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(mIRa.class.getResource("/JavaGUI/resources/pill.png"))); //import image
		frame.setForeground(new Color(0, 0, 0));
		frame.setBounds(150, 150, 759, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.gray);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {650};
		gridBagLayout.rowHeights = new int[]{69, 40, 40, 365, 0};
		gridBagLayout.columnWeights = new double[]{0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
				lblNewLabel = new JLabel("");
				lblNewLabel.setIcon(new ImageIcon(mIRa.class.getResource("/JavaGUI/resources/logo2.PNG")));
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel.gridx = 0;
				gbc_lblNewLabel.gridy = 0;
				frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
				
						
		
				tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				tabbedPane.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
				GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
				gbc_tabbedPane.fill = GridBagConstraints.BOTH;
				gbc_tabbedPane.gridx = 0;
				gbc_tabbedPane.gridy = 3;
				frame.getContentPane().add(tabbedPane, gbc_tabbedPane);
					//add different panels
						
						scrollPane_2 = new JScrollPane();
						scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						tabbedPane.addTab("Conditions", null, scrollPane_2, null);
						
						conditionPane = new JPanel();
						scrollPane_2.setViewportView(conditionPane);
						conditionPane.setLayout(new BoxLayout(conditionPane, BoxLayout.Y_AXIS));
						
						scrollPane_1 = new JScrollPane();
						scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						tabbedPane.addTab("Drugs", null, scrollPane_1, null);
						
						drugPane = new JPanel();
						scrollPane_1.setViewportView(drugPane);
						drugPane.setLayout(new BoxLayout(drugPane, BoxLayout.Y_AXIS));
						
						scrollPane = new JScrollPane();
						scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						tabbedPane.addTab("Reviews", null, scrollPane, null);
						
						reviewPane = new JPanel();
						scrollPane.setViewportView(reviewPane);
						reviewPane.setLayout(new BoxLayout(reviewPane, BoxLayout.Y_AXIS));
						txtEnterTheDrugs = new JTextField();
						txtEnterTheDrugs.getDocument().addDocumentListener(new DocumentListener() {
							// implement the methods
							public void changedUpdate(DocumentEvent e) {
								warn();
							}

							public void removeUpdate(DocumentEvent e) {
							    warn();
							}

							public void insertUpdate(DocumentEvent e) {
							    warn();
							}

							public void warn() {
								String search = txtEnterTheDrugs.getText();
								conditionPane.removeAll();
								if(search.length() != 0) {
									if(tabbedPane.getSelectedIndex() != 0) {
										tabbedPane.setSelectedIndex(0);
									}
									search = upperCaseWords(search).trim();
									List<String> a = t.autocomplete(search); //autocomplete the word
									System.out.println("SEARCH: \""+search + "\" Autocomplete Size: "+ a.size());
									for (String string : a) {
										JLabel j = new JLabel(string);
										j.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										j.setFont(new Font("Calibri", Font.BOLD, 20));
										j.addMouseListener(new MouseAdapter()  
										{  
										    public void mouseClicked(MouseEvent e)  
										    {  
//						    	System.out.println("Clicked: " + string + "!");
//						    	conditionPane.removeAll();
										    	tabbedPane.setSelectedIndex(1);										    	
										    	Condition c = l.get(string);
										    	drugPane.removeAll();
										    	for (Comparable drug : c.getDrugs()) {
										    		Drug d = (Drug) drug;
										    		JLabel k = new JLabel(d.getName());
										    		k.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
													k.setFont(new Font("Calibri", Font.BOLD, 20));
													drugPane.add(k);
													k.addMouseListener(new MouseAdapter()  
													{  
													    public void mouseClicked(MouseEvent e)  
													    {  // displaying reviews
													    	reviewPane.removeAll();
													    	tabbedPane.setSelectedIndex(2);
													    	for (Comparable review : d.getReviews()) {
													    		Review r = (Review) review;
													    		
													    		String rTitle = d.getName() + ":";
													    		JLabel title = new JLabel(rTitle);
													    		title.setFont(new Font("Calibri", Font.BOLD, 27));
													    		reviewPane.add(title);
													    		
													    	    DecimalFormat df = new DecimalFormat("#.0");
													    	    String srating = df.format(r.getSrating());
													    		JLabel star = new JLabel(srating);
													    		star.setFont(new Font("Calibri", Font.BOLD, 25));
													    		star.setIcon(new ImageIcon(mIRa.class.getResource("/JavaGUI/resources/gstar.png")));
													    		star.setAlignmentX(Component.LEFT_ALIGNMENT);
													    		reviewPane.add(star);
													    		
//									    		JLabel l = new JLabel("<html><br><html>");
//									    		reviewPane.add(l);
													    		
													    		String toSay = "The reviwer's experience:";
													    		JLabel say = new JLabel(toSay);
													    		say.setFont(new Font("Calibri", Font.BOLD, 23));
													    		reviewPane.add(say);
													    		
									
//									    		reviewPane.add(l);
													    		
													    		String toAdd = r.getReview();
													    		String labelText = String.format("<html><div WIDTH=%d>%s</div></html>", reviewPane.getSize().width - 10, toAdd);
//									    		toAdd = "<html>" + toAdd + "<html>";
													    		JLabel z = new JLabel(labelText);
													    		z.setFont(new Font("Calibri", Font.BOLD, 20));
													    		reviewPane.add(z);
													    		
							
//									    		reviewPane.add(l);
													    		
													    		/*String usefulness = "Usefulness:";
													    		JLabel use = new JLabel(usefulness);
													    		use.setFont(new Font("Calibri", Font.BOLD, 21));
													    		reviewPane.add(use);*/
													    		
//									    		reviewPane.add(l);
													    		
													    		/*String useful = Integer.toString(r.getUseful());
													    		JLabel u = new JLabel(useful);
													    		u.setFont(new Font("Calibri", Font.BOLD, 20));
													    		reviewPane.add(u);*/
													    	}
													    	reviewPane.revalidate();
													    	reviewPane.repaint();
													    	
													    }
													});
													
												}
										    	drugPane.updateUI();
										    }  
										}); 
										conditionPane.add(j);
									}
								}
								conditionPane.updateUI();
							}
						});
						txtEnterTheDrugs.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent arg0) {
								txtEnterTheDrugs.setText("");
							}
						});
						txtEnterTheDrugs.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
						txtEnterTheDrugs.setText("Enter the condition here");
						txtEnterTheDrugs.setForeground(new Color(211, 211, 211));
						GridBagConstraints gbc_txtEnterTheDrugs = new GridBagConstraints();
						gbc_txtEnterTheDrugs.fill = GridBagConstraints.BOTH;
						gbc_txtEnterTheDrugs.insets = new Insets(0, 0, 5, 0);
						gbc_txtEnterTheDrugs.gridx = 0;
						gbc_txtEnterTheDrugs.gridy = 1;
						frame.getContentPane().add(txtEnterTheDrugs, gbc_txtEnterTheDrugs);
						txtEnterTheDrugs.setColumns(10);
		

	}
}

