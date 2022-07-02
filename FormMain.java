package Final;
import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

public class FormMain extends JFrame {
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Declare JObjects on JFrame
	 */
	
	private JPanel contentPane;
	private JLabel lbTitle;
	private JPanel pnImitiate;
	private JPanel pnArray;
	private JPanel pnCreateArray;
	private JLabel lbNum, lbMaxNum;
	private JSpinner spNum;
	private JButton btnCreateArray, btnDeleteArray, btnSetZero;
	private JButton btnRandom;
	private JPanel pnAlgorithm;
	private JRadioButton rdBubbleSort, rdQuickSort, rdHeapSort;
	private JPanel pnControl;
	private JButton btnSort, btnStop;
	public int num;
	private JLabel[] lbArrays;
	private int[] array;
	private Thread[] threads = new Thread[1000000];
	private int curT = -1;
	private int time = 50;
	private int step = 0;	
	private int[] lbI = new int[50];
    private int[] lbJ = new int[50];
    private int[] lbL = new int[50];
    private int[] lbR = new int[50];
    private int[] oriLocat = new int[15];
    private JLabel lbPoint1 = new JLabel();
    private JLabel lbPoint2 = new JLabel();
    private JLabel lbPointM = new JLabel();
    private Color processingColor = new Color(255, 153, 153);
    private Color selectedGreen = new Color(153, 255, 153);
    private Color selectedYellow = new Color(255, 255, 153);
    
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setLockAndFeel();
				try {
					FormMain frame = new FormMain();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //set JFrame full screen
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormMain() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("Sorting Algorithm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1376, 742);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lbTitle = new JLabel("Sorting Algorithm");
		lbTitle.setBackground(SystemColor.menu);
		lbTitle.setBounds(5, 5, 1286, 28);
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane.add(lbTitle);
		
		pnArray = new JPanel();
		pnArray.setBounds(290, 415, 319, 262);
		pnArray.setBackground(SystemColor.menu);
		pnArray.setBorder(new TitledBorder(null, "Data Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pnArray);
		
		pnAlgorithm = new JPanel();
		pnAlgorithm.setBackground(SystemColor.menu);
		pnAlgorithm.setBounds(619, 415, 187, 262);
		pnAlgorithm.setBorder(new TitledBorder(null, "Choose Algorithm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pnAlgorithm);
		
		pnControl = new JPanel();
		pnControl.setBackground(SystemColor.menu);
		pnControl.setBounds(817, 415, 187, 262);
		pnControl.setBorder(new TitledBorder(null, "Control", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pnControl);

		pnControl.setLayout(null);
		
		btnSort = new JButton("Sort");
		btnSort.setBackground(SystemColor.activeCaption);
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if (!isSorted()) {
					setState(3);
					for (int i = 0; i < num; i++) 
						lbArrays[i].setForeground(Color.BLUE);
					
					if (rdBubbleSort.isSelected())
						BubbleSort();
					
					if (rdQuickSort.isSelected())
						QuickSort();
					
					if (rdHeapSort.isSelected()) {
						HeapSort();
						threadReLocate();
					}
					waitEnd();						
				}
			}
		});
		btnSort.setBounds(39, 49, 120, 25);
		pnControl.add(btnSort);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteArrays();
				setState(0);
			}
		});
		btnStop.setBackground(SystemColor.activeCaption);
		btnStop.setBounds(39, 111, 120, 25);
		pnControl.add(btnStop);
		pnAlgorithm.setLayout(null);
		
		rdBubbleSort = new JRadioButton("Bubble Sort");
		rdBubbleSort.setBounds(24, 46, 149, 23);
		pnAlgorithm.add(rdBubbleSort);
		
		pnCreateArray = new JPanel();
		pnCreateArray.setBackground(SystemColor.menu);
		pnCreateArray.setBorder(new TitledBorder(null, "Create array", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_pnArray = new GroupLayout(pnArray);
		gl_pnArray.setHorizontalGroup(
			gl_pnArray.createParallelGroup(Alignment.LEADING)
				.addComponent(pnCreateArray, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
		);
		gl_pnArray.setVerticalGroup(
			gl_pnArray.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnArray.createSequentialGroup()
					.addComponent(pnCreateArray, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(123, Short.MAX_VALUE))
		);
		
		lbNum = new JLabel("Number of elements:");
		lbNum.setBounds(16, 27, 139, 20);
		
		SpinnerModel sm = new SpinnerNumberModel(2, 2, 15, 1);
		spNum = new JSpinner(sm);
		spNum.setBounds(160, 25, 120, 25);
		JFormattedTextField txt = ((JSpinner.NumberEditor) spNum.getEditor()).getTextField();
		((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
		
		btnCreateArray = new JButton("Create array");
		btnCreateArray.setBackground(SystemColor.activeCaption);
		btnCreateArray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createArrays();
			}
		});
		btnCreateArray.setBounds(160, 73, 120, 25);
		
		btnDeleteArray = new JButton("Detete array");
		btnDeleteArray.setBackground(SystemColor.activeCaption);
		btnDeleteArray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteArrays();		
				setState(0);
			}
		});
		btnDeleteArray.setBounds(160, 108, 120, 25);
		
		btnSetZero = new JButton("Set 0");
		btnSetZero.setBackground(SystemColor.activeCaption);
		btnSetZero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setZero();
			}
		});
		btnSetZero.setBounds(16, 108, 120, 25);
		
		lbMaxNum = new JLabel("(Maximum 15)");
		lbMaxNum.setHorizontalAlignment(SwingConstants.CENTER);
		lbMaxNum.setBounds(10, 47, 109, 14);
		
		btnRandom = new JButton("Create Random");
		btnRandom.setBackground(SystemColor.activeCaption);
		btnRandom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRandom();
			}
		});
		btnRandom.setBounds(16, 73, 120, 25);
		
		pnCreateArray.setLayout(null);
		pnCreateArray.add(lbNum);
		pnCreateArray.add(btnSetZero);
		pnCreateArray.add(btnCreateArray);
		pnCreateArray.add(spNum);
		pnCreateArray.add(btnDeleteArray);
		pnCreateArray.add(lbMaxNum);
		pnCreateArray.add(btnRandom);

		pnArray.setLayout(gl_pnArray);
		
		pnImitiate = new JPanel();
		pnImitiate.setBackground(SystemColor.menu);
		pnImitiate.setBorder(new TitledBorder(null, "Visually Demonstrate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnImitiate.setBounds(5, 44, 1355, 360);
		contentPane.add(pnImitiate);
		pnImitiate.setLayout(null);
                
        lbPoint1.setSize(50, 25);
        lbPoint1.setOpaque(true);
        lbPoint1.setLocation(50, 50);
        lbPoint1.setFont(new Font("Helvetica", Font.BOLD, 17));
        lbPoint1.setBackground(SystemColor.menu);
        lbPoint1.setHorizontalAlignment(SwingConstants.CENTER);
        lbPoint1.setVerticalAlignment(SwingConstants.CENTER);
        pnImitiate.add(lbPoint1);
        pnImitiate.add(lbPoint2);
        lbPoint2.setSize(50, 25);
        lbPoint2.setOpaque(true);
        lbPoint2.setLocation(50, 50);
        lbPoint2.setFont(new Font("Helvetica", Font.BOLD, 17));
        lbPoint2.setBackground(SystemColor.menu);
        lbPoint2.setHorizontalAlignment(SwingConstants.CENTER);
        lbPoint2.setVerticalAlignment(SwingConstants.CENTER);
        pnImitiate.add(lbPointM);
        lbPointM.setSize(50, 25);
        lbPointM.setOpaque(true);
        lbPointM.setLocation(50, 50);
        lbPointM.setFont(new Font("Helvetica", Font.BOLD, 17));
        lbPointM.setBackground(SystemColor.menu);
        lbPointM.setHorizontalAlignment(SwingConstants.CENTER);
        lbPointM.setVerticalAlignment(SwingConstants.CENTER);
		
		rdBubbleSort.setSelected(true);
		
		rdQuickSort = new JRadioButton("Quick Sort");
		rdQuickSort.setBounds(24, 93, 149, 23);
		pnAlgorithm.add(rdQuickSort);
		
		rdHeapSort = new JRadioButton("Heap Sort");
		rdHeapSort.setBounds(24, 138, 149, 23);
		pnAlgorithm.add(rdHeapSort);
		
		setState(0);
	}
	
	/*
	 * Set Lock And Feel
	 */
	public static void setLockAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    
		}
	}
	
	/*
	 * Set state and manage GUI
	 */
	public void setState(int state) {
		switch (state) {
		case 0: //first state, haven't created arrays.
			btnCreateArray.setEnabled(true);
			btnDeleteArray.setEnabled(false);
			btnSetZero.setEnabled(false);

			btnRandom.setEnabled(false);

			rdBubbleSort.setEnabled(true);
			rdQuickSort.setEnabled(true);
			rdHeapSort.setEnabled(true);
			
			btnSort.setEnabled(false);
			btnStop.setEnabled(false);
			break;
			
		case 1: //created arrays, be waiting to set value arrays.
			btnDeleteArray.setEnabled(true);
			btnSetZero.setEnabled(true);
			
			btnRandom.setEnabled(true);

			rdBubbleSort.setEnabled(true);
			rdQuickSort.setEnabled(true);
			rdHeapSort.setEnabled(true);
			break;
			
		case 2: //be set values, ready to sort
			btnDeleteArray.setEnabled(true);
			btnSetZero.setEnabled(true);

			btnRandom.setEnabled(true);

			rdBubbleSort.setEnabled(true);
			rdQuickSort.setEnabled(true);
			rdHeapSort.setEnabled(true);

			btnSort.setEnabled(true);
			btnStop.setEnabled(false);
			break;
			
		case 3: //sorting
			btnCreateArray.setEnabled(true);
			btnDeleteArray.setEnabled(true);
			btnSetZero.setEnabled(false);
			
			btnRandom.setEnabled(false);
			
			rdBubbleSort.setEnabled(false);
			rdQuickSort.setEnabled(false);
			rdHeapSort.setEnabled(false);

			btnSort.setEnabled(false);
			btnStop.setEnabled(true);
			break;
			
		case 4: //sort done
			btnCreateArray.setEnabled(true);
			btnDeleteArray.setEnabled(true);
			btnSetZero.setEnabled(true);
			
			btnRandom.setEnabled(true);

			rdBubbleSort.setEnabled(true);
			rdQuickSort.setEnabled(true);
			rdHeapSort.setEnabled(true);

			btnSort.setEnabled(true);
			btnStop.setEnabled(true);
			break;
			default:
				btnCreateArray.setEnabled(true);
				btnDeleteArray.setEnabled(false);
				btnSetZero.setEnabled(false);

				btnRandom.setEnabled(false);
				
				rdBubbleSort.setEnabled(true);
				rdQuickSort.setEnabled(true);
				rdHeapSort.setEnabled(true);

				btnSort.setEnabled(false);
				btnStop.setEnabled(false);
		}
	}
	
	/*
	 * Doing with arrays
	 */
	public void createArrays() {
		
		//delete previous arrays and set number elements of array
		deleteArrays();
		num = (Integer)spNum.getValue();
		
		lbArrays = new JLabel[num];
		array = new int[num];
		
		for (int i = 0; i < num; i++) {
			
			//create label, set text "0"
			lbArrays[i] = new JLabel("0");
			array[i] = 0;
			pnImitiate.add(lbArrays[i]);
			lbArrays[i].setText(String.valueOf(array[i]));
			
			//set size label
			lbArrays[i].setSize(50,50);
			lbArrays[i].setOpaque(true);
			lbArrays[i].setForeground(Color.blue);
			
			//set location label
			if (i == 0)
				lbArrays[i].setLocation(((int) ((18 - num) * 0.5) * 70) + 100, 150);
			else
				lbArrays[i].setLocation(lbArrays[i-1].getX() + 70, 150);
			
			//set fonts
			lbArrays[i].setFont(new Font("Tahoma", Font.PLAIN, 30));
			
			//set background color
			lbArrays[i].setBackground(SystemColor.inactiveCaption);
			//set text alignment center
			lbArrays[i].setHorizontalAlignment(SwingConstants.CENTER); 
			lbArrays[i].setVerticalAlignment(SwingConstants.CENTER);
		}
                
        pnImitiate.add(lbPoint1);
        pnImitiate.add(lbPoint2);
        pnImitiate.add(lbPointM);
                
		pnImitiate.setVisible(true);
		pnImitiate.validate();
		pnImitiate.repaint();
		setState(1);
	}
	
	public void deleteArrays() {
		for (int i = 0; i < num; i++) {
			lbArrays[i].setText("0");
			array[i] = 0;
			lbArrays[i].setVisible(false);
			pnImitiate.remove(lbArrays[i]);
		}
                
                lbPoint1.setText("");
                lbPoint2.setText("");
                lbPointM.setText("");
                pnImitiate.remove(lbPoint1);
                pnImitiate.remove(lbPoint2);
                pnImitiate.remove(lbPointM);
		
		for (int i = 0; i < curT; i++) {
			try {
					threads[i].interrupt();
			} 
			catch (Exception e) {
				
			}
		}
		curT = -1;
		
		pnImitiate.revalidate();
		pnImitiate.repaint();
		setState(0);
	}
	
	public void setZero() {
		for (int i = 0; i < num; i++) {
			lbArrays[i].setText("0");
			array[i] = 0;
		}
		createArrays();
		pnImitiate.revalidate();
		pnImitiate.repaint();
		setState(1);
	}
	
	public void createRandom() {
		Random rand = new Random();
		for (int i = 0; i < num; i++) {
			int ranNum = rand.nextInt(101) + 0;
			lbArrays[i].setText(String.valueOf(ranNum));
			lbArrays[i].setForeground(Color.BLUE);
			array[i] = ranNum;
		}
		pnImitiate.setVisible(true);
		pnImitiate.validate();
		pnImitiate.repaint();
		setState(2);
	}
	
    /*
	 * Sort and Swap
	 */
	public void Swap(JLabel lb1, JLabel lb2) {
		int x1 = lb1.getX();
		int x2 = lb2.getX();
		curT ++;
		
		int cur = curT;
		threads[cur] = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {
		    		if (cur != 0) {
			    		threads[cur-1].join();
			    	}
		    		
		    		lb1.setBackground(processingColor);
		    		lb2.setBackground(processingColor);
			        while (lb1.getY() > 100) {
			        	lb1.setLocation(lb1.getX(), lb1.getY() - 10);
			        	lb2.setLocation(lb2.getX(), lb2.getY() + 10);
                        lbPointM.setLocation(x2, lbPointM.getY() + 10);
			        	Thread.sleep(time);
			        }
			        while (lb1.getX() < x2) {
			        	lb1.setLocation(lb1.getX() + 10, lb1.getY());
			        	lb2.setLocation(lb2.getX() - 10, lb2.getY());
                        lbPointM.setLocation(lb2.getX(), 250);
			        	Thread.sleep(time);
			        }
			        while (lb1.getY() < 140) {
			        	lb1.setLocation(lb1.getX(), lb1.getY() + 10);
			        	lb2.setLocation(lb2.getX(), lb2.getY() - 10);
                        lbPointM.setLocation(x1, lbPointM.getY() - 10);
			        	Thread.sleep(time);
			        }
			        String txtLb1 = lb1.getText();
			        lb1.setText(lb2.getText());
			        lb2.setText(txtLb1);
			        lb1.setLocation(x1, 150);
		        	lb2.setLocation(x2, 150);
		        	lb1.setBackground(SystemColor.inactiveCaption);
		        	lb2.setBackground(SystemColor.inactiveCaption);
		    	} catch (Exception e) {
		    	}
		    }
		});
		threads[cur].start();
	}
        
	public void setlbPoint(JLabel lbPoint, int i, String s) {
        curT ++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0)
                        threads[cur - 1].join();
                    if (i == -1) {
                        lbPoint.setText("");
                        return;
                    }
                    if (s.charAt(0) == 'm') {
                        lbPoint.setLocation(lbArrays[i].getX(), 200);
                        lbPoint.setText(s);
                    } else if (s.charAt(0) == 'k') {
                        lbPoint.setLocation(oriLocat[i], 200);
                        lbPoint.setText(s + i);
                    } else {
                        lbPoint.setLocation(lbArrays[i].getX(), 275);
                        lbPoint.setText(s + i);
                    }
                } catch (Exception e){}
            }
        });
        threads[cur].start();
    }
	/*
	 * Bubble Sort
	 */
    public void BubbleSort() {
    	int i, j;
    	for (i = 0; i< num; i++) {
    		setlbPoint(lbPoint1, i, "i = ");
    		for (j = num - 1; j > i; j--) {
    			setlbPoint(lbPoint2, j, "j = ");
    			if(array[j]< array[j-1]) {
    				int temp = array[j];
    				array[j] = array[j - 1];
    				array[j - 1] = temp;
    				Swap(lbArrays[j - 1], lbArrays[j]);
    			}
    		}
    	}
    }
    /*
	 * Quick Sort
	 */
    public void QuickSort() {
    	QuickSortAl(0, num - 1);
    	QuickSortAnimation();
    	step = 0;
    }
        
    public void Coloring(JLabel lb1, Color c) {
    	curT ++;
    	int cur = curT;
    	threads[cur] = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
    				if (cur != 0)
    					threads[cur - 1].join();
                        lb1.setBackground(c);
                        Thread.sleep(time);
    			} catch (Exception e) {}
            }
        });
    	threads[cur].start();
    }
        
    public void Coloring(int left, int right, Color c) {
    	curT ++;
    	int cur = curT;
    	threads[cur] = new Thread(new Runnable() {
    		@Override
    		public void run() {
    			try {
    				if (cur != 0)
    					threads[cur - 1].join();
    				int i = left;
    				while (i <= right) {
    					if (i != (left + right) / 2)
    						lbArrays[i].setBackground(c);
    					i ++;
                    }
    				Thread.sleep(time);
                } catch (Exception e) {}
            }
        });
    	threads[cur].start();
    }
        
    public void QuickSortAnimation() {
    	int s, i, j;
    	for (s = 0; s < step; s ++) {
    		i = lbI[s];
    		j = lbJ[s];
    		setlbPoint(lbPoint1, i, "i = ");
    		setlbPoint(lbPoint2, j, "j = ");
    		if (i != j) {
    			Coloring(lbArrays[(lbL[s] + lbR[s]) / 2], selectedGreen);
    			Coloring(lbL[s], lbR[s], selectedYellow);
    			Swap(lbArrays[i], lbArrays[j]);
            }
    		if (lbL[s + 1] + lbR[s + 1] != lbL[s] + lbR[s]) {
    			Coloring(lbArrays[(lbL[s] + lbR[s]) / 2], SystemColor.inactiveCaption);
    			Coloring(lbL[s], lbR[s], SystemColor.inactiveCaption);
            }
        }
    }
        
    public void QuickSortAl(int left, int right) {
    	int i, j, x;
    	x = array[(left + right) / 2];
    	i = left; j = right;
    	while (i < j) {
    		while (array[i] < x) {
    			i ++;
    		}
    		while (array[j] > x) {
    			j --;
    		}
    		if (i <= j) {
    			if (array[i] != array[j]) {
    				int temp = array[i];
    				array[i] = array[j];
    				array[j] = temp;
    				if (i != j) {
    					lbL[step] = left; lbR[step] = right;
    					lbI[step] = i; lbJ[step] = j;
    					step ++;
    				}
    			}
    			i ++; j --;
    		}
    	}
    	if (left < j)
    		QuickSortAl(left, j);
    	if (i < right)
    		QuickSortAl(i, right);            
    }
    
    public void HeapSort() {
        int r;
        int xend = ((int) ((18 - num) * 0.5) * 70) + 100 + (num - 1) * 70;
        HeapLocationInit();
        CreateHeap();
        r = num - 1;
        while (r > 0) {
            int x = array[0];
            array[0] = array[r];
            array[r] = x;
            SwapHeapEnd(lbArrays[0], lbArrays[r], xend);
            xend -= 70;
            r --;
            if (r > 0) {
                Shift(0, r);
            }
        }
        SwapHeapEnd(lbArrays[0], null, xend);
    }
    
    public void CreateHeap() {
        int l;
        l = num / 2 - 1;
        while (l >= 0) {
            Shift(l, num - 1);
            l --;
        }
    }
    
    public void Shift(int l, int r) {
        int x, i ,j;
        i = l;
        j = i * 2 + 1;
        x = array[i];
            while (j <= r) {
                if (j < r) {
                    if (array[j] < array[j + 1]) {
                        j++;
                    }
                }
                if (array[j] <= x) {
                    return;
                } else {
                    array[i] = array[j];
                    array[j] = x;
                    SwapinHeap(lbArrays[i], lbArrays[j]);
                    i = j;
                    j = i * 2 + 1;
                    x = array[i];
                }
            }
        
    }
    
    public void HeapLocationInit() {
        int i, j = 0;
        int row = 1;
        int maxirow = 0;
        int[] xi = {600, 480, 720, 420, 540, 660, 780, 390, 450, 510, 570, 630, 690, 750, 810};
        int[] yi = {60, 110, 160, 210};
        MovetoLocation(lbArrays[0], xi[0], yi[0]);
        for (i = 0; i <= (num - 1) / 2; i ++) {
            if (i > maxirow) {
                row ++;
                maxirow = maxirow * 2 + 2;
            }
            j = i * 2 + 1;
            while (j <= i * 2 + 2 && j < num) {
                if (j == i * 2 + 1) {
                    MovetoLocation(lbArrays[j], xi[j], yi[row]);
                } else {
                    MovetoLocation(lbArrays[j], xi[j], yi[row]);
                }
                j ++;
            }
        }
    }
    
    public void MovetoLocation(JLabel lb1, int x, int y) {
        curT ++;
        
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    lb1.setBackground(processingColor);
                    int x1 = lb1.getX();
                    int y1 = lb1.getY();
                    if (x1 < x && y1 < y) {
                        while (lb1.getX() < x) {
                            lb1.setLocation(lb1.getX() + 10, y1);
                            Thread.sleep(time);
                        }
                        while (lb1.getY() < y) {
                            lb1.setLocation(x, lb1.getY() + 10);
                            Thread.sleep(time);
                        }
                    } else if (x1 >= x && y1 < y) {
                        while (lb1.getX() > x) {
                            lb1.setLocation(lb1.getX() - 10, y1);
                            Thread.sleep(time);
                        }
                        while (lb1.getY() < y) {
                            lb1.setLocation(x, lb1.getY() + 10);
                            Thread.sleep(time);
                        }
                    } else if (x1 < x && y1 >= y) {
                        while (lb1.getY() > y) {
                            lb1.setLocation(x1, lb1.getY() - 10);
                            Thread.sleep(time);
                        }
                        while (lb1.getX() < x) {
                            lb1.setLocation(lb1.getX() + 10, y);
                            Thread.sleep(time);
                        }
                    } else if (x1 >= x && y1 >= y) {
                        while (lb1.getY() > y) {
                            lb1.setLocation(x1, lb1.getY() - 10);
                            Thread.sleep(time);
                        }
                        while (lb1.getX() > x) {
                            lb1.setLocation(lb1.getX() - 10, y);
                            Thread.sleep(time);
                        }
                    }
                    lb1.setBackground(SystemColor.inactiveCaption);
                } catch (Exception e) {
                }
            }
        });
        threads[cur].start();
    }
    
    public void setBackgroundMoving(JLabel lb1, JLabel lb2) {
    	lb1.setOpaque(true);
    	lb2.setOpaque(true);
    	
    	lb1.setBackground(processingColor);
    	lb2.setBackground(processingColor);
    }
    
    public void setBackgroundDone(JLabel lb1, JLabel lb2) {
    	lb1.setOpaque(true);
    	lb2.setOpaque(true);
    	
    	lb1.setBackground(SystemColor.inactiveCaption);
    	lb2.setBackground(SystemColor.inactiveCaption);
    }
    
    public void SwapwithoutMoving(JLabel lb1, JLabel lb2) {
        curT ++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur - 1].join();
                    }
                    String txtLb1 = lb1.getText();
                    lb1.setText(lb2.getText());
                    lb2.setText(txtLb1);
                    int x = lb1.getX();
                    int y = lb1.getY();
                    lb1.setLocation(lb2.getX(), lb2.getY());
                    lb2.setLocation(x, y);
                } catch (Exception e) {
                }
            }
        });
        threads[cur].start();
    }
    
    public void SwapinHeap(JLabel lb1, JLabel lb2) {
        curT ++;
        int cur = curT;
        threads[cur] = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (cur != 0) {
                        threads[cur-1].join();
                    }
                    setBackgroundMoving(lb1, lb2);
                    int x1 = lb1.getX();
                    int x2 = lb2.getX();
                    int y1 = lb1.getY();
                    int y2 = lb2.getY();
                    while (lb2.getY() > y1) {
                        if (lb1.getY() > y1 - 50)
                            lb1.setLocation(lb1.getX(), lb1.getY() - 10);
                        lb2.setLocation(lb2.getX(), lb2.getY() - 10);
                        Thread.sleep(time);
                    }
                    if (x2 < x1) {
                        while (lb2.getX() < x1) {
                            lb1.setLocation(lb1.getX() - 10, lb1.getY());
                            lb2.setLocation(lb2.getX() + 10, lb2.getY());
                            Thread.sleep(time);
                        }
                    } else {
                        while (lb2.getX() > x1) {
                            lb1.setLocation(lb1.getX() + 10, lb1.getY());
                            lb2.setLocation(lb2.getX() - 10, lb2.getY());
                            Thread.sleep(time);
                        }
                    }
                    while (lb1.getY() < y2) {
                        lb1.setLocation(lb1.getX(), lb1.getY() + 10);
                        Thread.sleep(time);
                    }
                    String txtLb1 = lb1.getText();
                    lb1.setText(lb2.getText());
                    lb2.setText(txtLb1);
                    lb1.setLocation(x1, y1);
                    lb2.setLocation(x2, y2);
                    setBackgroundDone(lb1, lb2);
                } catch (Exception e) {
                }
            }
        });
        threads[cur].start();
    }
    
    public void SwapHeapEnd(JLabel lb1, JLabel lb2, int xend) {
    	MovetoLocation(lb1, xend, 260);
    	MovetoLocation(lb2, 600, 60);
    	SwapwithoutMoving(lb1, lb2);
    }
    
    public void threadReLocate() {
    	curT++;
		
		int cur = curT;
		threads[cur] = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	try {
		    		if (cur != 0) {
			    		threads[cur-1].join();
			    	}
		    		reLocate();
		    	} catch (Exception e) {
		    		
		    	}
		    }
		});
		threads[cur].start();
    }
    
    public void reLocate() {
    	for (int i = 0; i < num; i++) {
			//set location label
			if (i == 0)
				lbArrays[i].setLocation(((int) ((18 - num) * 0.5) * 70) + 100, 150);
			else
				lbArrays[i].setLocation(lbArrays[i-1].getX() + 70, 150);
    	}
    }
    
    /*
     * Waiting to end sorting
     */
        public void waitEnd() {
        	curT++;
    		
    		int cur = curT;
    		threads[cur] = new Thread(new Runnable() {
    		    @Override
    		    public void run() {
    		    	try {
    		    		if (cur != 0) {
    			    		threads[cur-1].join();
    			    	}
    		    		setState(4);
    		    		for (int i = 0; i < num; i++) {
    		    			lbArrays[i].setForeground(Color.darkGray);
    		    		}
                        lbPoint1.setText("");
                        lbPoint2.setText("");
                        lbPointM.setText("");
    		    	} catch (Exception e) {
    		    		
    		    	}
    		    }
    		});
    		threads[cur].start();
        }
    
    public boolean isSorted() {
    	for (int i = 0; i < array.length - 1; i++)
    		if (array[i] > array[i+1])
    			return false;
    	return true;
    }
}

