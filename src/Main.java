import java.awt.Color; //Import Libraries
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main { //Setup Initial Variable Values
	private static int period = 1;
	private static String periodString = "1ST";
	private static int hscore = 0;
	private static int vscore = 0;
	private static int hbonus = 0;
	private static int vbonus = 0;
	private static int hdbonus = 0;
	private static int vdbonus = 0;
	private static int hfouls = 0;
	private static int vfouls = 0;
	private static int htol = 5;
	private static int vtol = 5;
	private static int hposs = 0;
	private static int vposs = 0;
	public static boolean timeOn = false;
	public static int min = 0;
	public static int sec = 0;
	public static int tenth = 0;
	public static String clock = "0.0";
	public static String csvFilePath;
	public static Thread clockThread;
	public static ArrayList<String> inputs = new ArrayList<String>();
	public static JLabel clockLabel = new JLabel ("Clock: 0.0");
	public static JLabel periodLabel = new JLabel ("Period: 1ST");
	public static JLabel hscoreLabel = new JLabel ("Score: 0");
	public static JLabel vscoreLabel = new JLabel ("Score: 0");
	public static JLabel homeTOL = new JLabel ("TOL: 5");
	public static JLabel visitorTOL = new JLabel ("TOL: 5");
	public static JLabel hFoulsLabel = new JLabel ("Fouls: 0");
	public static JLabel vFoulsLabel = new JLabel ("Fouls: 0");
	public static JToggleButton finalT = new JToggleButton ("Final");
	public static JToggleButton half = new JToggleButton ("Half");
	public static JToggleButton freeze = new JToggleButton ("Freeze");
	public static boolean freezeVar = false;
	public static JTextField filePath = new JTextField ("C:/Users/LiveStream/Desktop/scoreboard.json");
	
	public static void main (String [] args) { //Set up the clock for the when the contoller is running the timer
		Clock clockrunnable = new Clock();
		//Create an image to use as the logo
		ImageIcon icon = new ImageIcon("JCD LIVE LOGO 1 .png");
		Image logo = icon.getImage();
		
		//Create a JFrame to Select a File location
		JFrame selectFile = new JFrame (); //Set up a window to select a file
		selectFile.setTitle("Scoreboard Controller: Select File");
		selectFile.setSize(500, 250);
		selectFile.setIconImage(logo);
		selectFile.setLayout(null);
		selectFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Components for the Select File Frame
		JLabel instructions = new JLabel ("Please Enter the File path of where the .json file should be located:");
		instructions.setBounds(20, 20, 500, 15);
		selectFile.add(instructions);
		JTextField filePath = new JTextField ("C:/Users/LiveStream/Desktop/scoreboard.json");
		filePath.setBounds(20, 40, 300, 20);
		selectFile.add(filePath);
		JButton browse = new JButton ("Browse");
		browse.setBounds(340, 40, 100, 20);
		selectFile.add(browse);
		JButton enter = new JButton ("Go to Scoreboard Controller");
		enter.setBounds(125, 85, 250, 20);
		selectFile.add(enter);
		JFileChooser chooser = new JFileChooser();
		selectFile.setVisible(true);
		
		//Create a JFrame for Controller
		JFrame frame = new JFrame();
		frame.setTitle("Scoreboard Controller");
		frame.setSize(800, 550);
		frame.setLayout(null);
		frame.setIconImage(logo);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Setup the controller layout
		periodLabel.setBounds(10,10,100, 20);
		frame.add(periodLabel);
		JButton periodMinus = new JButton ("-");
		periodMinus.setBounds(100, 10, 50, 20);
		frame.add(periodMinus);

		JButton periodPlus = new JButton ("+");
		periodPlus.setBounds(150, 10, 50, 20);
		frame.add(periodPlus);
		
		
		JTextField home = new JTextField ("Home:");
		home.setBounds(30, 50, 100, 20);
		frame.add(home);
		hscoreLabel.setBounds(30, 110, 100, 20);
		frame.add(hscoreLabel);
		JToggleButton homePoss = new JToggleButton ("<");
		homePoss.setBounds(30, 80 , 50, 20);
		frame.add(homePoss);
		JToggleButton visitorPoss = new JToggleButton (">");
		visitorPoss.setBounds(370, 80 , 50, 20);
		frame.add(visitorPoss);
		
		
		JButton h3 = new JButton ("+3");
		h3.setBounds(30, 140, 50, 20);
		frame.add(h3);

		
		JButton h2 = new JButton ("+2");
		h2.setBounds(30, 170, 50, 20);
		frame.add(h2);

		
		JButton h1 = new JButton ("+1");
		h1.setBounds(30, 200, 50, 20);
		frame.add(h1);

		
		JButton hm = new JButton ("-1");
		hm.setBounds(30, 230, 50, 20);
		frame.add(hm);

		homeTOL.setBounds(30, 260, 50, 20);
		frame.add(homeTOL);
		
		JButton hTOLMinus = new JButton ("-");
		hTOLMinus.setBounds(30,290, 50, 20);
		frame.add(hTOLMinus);

		JButton hTOLPlus = new JButton ("+");
		hTOLPlus.setBounds(80, 290, 50, 20);
		frame.add(hTOLPlus);

		hFoulsLabel.setBounds(30, 320, 100, 20);
		frame.add(hFoulsLabel);
		
		JButton hfoulsMinus = new JButton ("-");
		hfoulsMinus.setBounds(30, 350, 50, 20);
		frame.add(hfoulsMinus);

		JButton hfoulsPlus = new JButton ("+");
		hfoulsPlus.setBounds(80, 350, 50, 20);
		frame.add(hfoulsPlus);
		//////////////////////////////////////////////////////////////////////////////////
		JButton v3 = new JButton ("+3");
		v3.setBounds(370, 140, 50, 20);
		frame.add(v3);
		
		JButton v2 = new JButton ("+2");
		v2.setBounds(370, 170, 50, 20);
		frame.add(v2);
		
		JButton v1 = new JButton ("+1");
		v1.setBounds(370, 200, 50, 20);
		frame.add(v1);
		
		JButton vm = new JButton ("-1");
		vm.setBounds(370, 230, 50, 20);
		frame.add(vm);
		
		visitorTOL.setBounds(370, 260, 50, 20);
		frame.add(visitorTOL);
		JButton vTOLMinus = new JButton ("-");
		vTOLMinus.setBounds(370, 290, 50, 20);
		frame.add(vTOLMinus);

		JButton vTOLPlus = new JButton ("+");
		vTOLPlus.setBounds(420, 290, 50, 20);
		frame.add(vTOLPlus);
		
		vFoulsLabel.setBounds(370, 320, 100, 20);
		frame.add(vFoulsLabel);
		
		JButton vfoulsMinus = new JButton ("-");
		vfoulsMinus.setBounds(370, 350, 50, 20);
		frame.add(vfoulsMinus);

		JButton vfoulsPlus = new JButton ("+");
		vfoulsPlus.setBounds(420, 350, 50, 20);
		frame.add(vfoulsPlus);

		JTextField visitor = new JTextField ("Visitor:");
		visitor.setBounds(370, 50, 100, 20);
		frame.add(visitor);
		vscoreLabel.setBounds(370, 110, 100, 20);
		frame.add(vscoreLabel);
		
		clockLabel.setBounds(30, 400, 100, 20);
		frame.add(clockLabel);
		
		JTextField minInput = new JTextField("0");
		minInput.setBounds(130, 400, 50, 20);
		frame.add(minInput);
		
		JLabel colon = new JLabel(":");
		colon.setBounds(190, 400, 50,20);
		frame.add(colon);
		
		JTextField secInput = new JTextField("00");
		secInput.setBounds(205, 400, 50, 20);
		frame.add(secInput);

		JLabel decimal = new JLabel (".");
		decimal.setBounds(265, 400, 50, 20);
		frame.add(decimal);

		JTextField tenthsecInput = new JTextField ("0");
		tenthsecInput.setBounds(280, 400, 50, 20);
		frame.add(tenthsecInput);
		
		JButton updateTime = new JButton("Update");
		updateTime.setBounds(260, 450, 100, 20);
		frame.add(updateTime);
		
		JToggleButton startTimer = new JToggleButton ("Time In");
		startTimer.setBounds(130, 430, 100, 50);
		frame.add(startTimer);
		
		JToggleButton overtime = new JToggleButton ("OT");
		overtime.setBounds(200, 10, 50, 20);
		frame.add(overtime);

		half.setBounds(250, 10, 100, 20);
		frame.add(half);

		finalT.setBounds(350, 10, 100, 20);
		frame.add(finalT);

		freeze.setBounds(250, 100, 100, 20);
		frame.add(freeze);
		
		//After the enter button on the select file window is pressed, create the file or overwrite the existing file
		enter.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		File file = new File (filePath.getText());
	    		try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		selectFile.setVisible(false);
	    		frame.setVisible(true);
	    		csvFilePath = filePath.getText();
	    	}  
	    });
		//Add a file explorer window to select a file
	    browse.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    	    FileNameExtensionFilter filter = new FileNameExtensionFilter("JavaScript Object Notation (.json)", "json");
	    	        chooser.setFileFilter(filter);
	    	    int returnVal = chooser.showOpenDialog(selectFile);
	    	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	       filePath.setText(chooser.getSelectedFile().getAbsolutePath());
	    	    }
	    	}  
	    }); 
		
		//Add functionality to all the buttons on the controller with some basketball automation such as period advancement and bonus and double bonus
		periodPlus.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (!(overtime.isSelected() || half.isSelected() || finalT.isSelected())) {
	    			period = period + 1;
	    			if (period == 1) {
	    				periodString = "1ST";
	    			} else if (period == 2) {
	    				periodString = "2ND";
	    			} else if (period == 3) {
	    				periodString = "3RD";
	    			} else if (period <= 20){
	    				periodString = period+"TH";
	    			} else {
	    				periodString = period+"";
	    			}
	    			updateFile (filePath.getText());
	    		}
	    	}  
	    }); 
		periodMinus.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (!(overtime.isSelected() || half.isSelected() || finalT.isSelected())) {
	    			period = period - 1;
	    			if (period == 1) {
	    				periodString = "1ST";
	    			} else if (period == 2) {
	    				periodString = "2ND";
	    			} else if (period == 3) {
	    				periodString = "3RD";
	    			} else if (period <= 20){
	    				periodString = period+"TH";
	    			} else {
	    				periodString = period+"";
	    			}
	    			updateFile (filePath.getText());
	    		}
	    	}  
	    }); 
		homePoss.addItemListener(new ItemListener() {
			   public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			        hposs = 1;
			        vposs = 0;
			        visitorPoss.setSelected(false);
			      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
			        hposs = 0;
			      }
			      updateFile(filePath.getText());
			   }
			});
			visitorPoss.addItemListener(new ItemListener() {
			   public void itemStateChanged(ItemEvent ev) {
			      if(ev.getStateChange()==ItemEvent.SELECTED){
			        hposs = 0;
			        vposs = 1;
			        homePoss.setSelected(false);
			      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
			        vposs = 0;
			      }
			      updateFile(filePath.getText());
			   }
			});
			h3.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hscore+=3;
		    		updateFile(filePath.getText());
		    	}  
		    }); 
			h2.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hscore+=2;
		    		updateFile(filePath.getText());
		    	}  
		    });
			h1.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hscore+=1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			hm.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hscore-=1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			hTOLMinus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		htol = htol - 1;
		    		updateFile(filePath.getText());
		    	}  
		    }); 
			hTOLPlus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		htol = htol+ 1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			hfoulsMinus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hfouls = hfouls - 1;
		    		if (hfouls >= 10) {
		    			vbonus = 1;
		    			vdbonus = 1;
		    		} else if (hfouls >= 7) {
		    			vbonus = 1;
		    			vdbonus = 0;
		    		} else {
		    			vbonus = 0;
		    			vdbonus = 0;
		    		}
		    		updateFile(filePath.getText());
		    	}  
		    }); 
			hfoulsPlus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hfouls = hfouls + 1;
		    		if (hfouls >= 10) {
		    			vbonus = 1;
		    			vdbonus = 1;
		    		} else if (hfouls >= 7) {
		    			vbonus = 1;
		    			vdbonus = 0;
		    		} else {
		    			vbonus = 0;
		    			vdbonus = 0;
		    		}
		    		updateFile(filePath.getText());
		    	}  
		    });
			v3.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vscore+=3;
		    		updateFile(filePath.getText());
		    	}  
		    }); 
			v2.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vscore+=2;
		    		updateFile(filePath.getText());
		    	}  
		    });
			v1.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vscore+=1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			vm.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vscore-=1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			vTOLMinus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vtol = vtol - 1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			vTOLPlus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vtol = vtol+ 1;
		    		updateFile(filePath.getText());
		    	}  
		    });
			vfoulsMinus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vfouls = vfouls - 1;
		    		if (vfouls >= 10) {
		    			hbonus = 1;
		    			hdbonus = 1;
		    		} else if (vfouls >= 7) {
		    			hbonus = 1;
		    			hdbonus = 0;
		    		} else {
		    			hbonus = 0;
		    			hdbonus = 0;
		    		}
		    		updateFile(filePath.getText());
		    	}  
		    }); 
			vfoulsPlus.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		vfouls = vfouls + 1;
		    		if (vfouls >= 10) {
		    			hbonus = 1;
		    			hdbonus = 1;
		    		} else if (vfouls >= 7) {
		    			hbonus = 1;
		    			hdbonus = 0;
		    		} else {
		    			hbonus = 0;
		    			hdbonus = 0;
		    		}
		    		updateFile(filePath.getText());
		    	}  
		    });
			updateTime.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		min = Integer.parseInt(minInput.getText());
		    		sec = Integer.parseInt(secInput.getText());
					tenth = Integer.parseInt(tenthsecInput.getText());
		    		updateFile(filePath.getText());
		    	}  
		    });
			startTimer.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ev) {
				      if(ev.getStateChange()==ItemEvent.SELECTED){
				        timeOn = true;
				        clockThread = new Thread (clockrunnable);
				        clockThread.start();
				      } else if(ev.getStateChange()==ItemEvent.DESELECTED){
				        timeOn = false;
				      }
				   }
				});
			overtime.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ev) {
					if (!(half.isSelected() || finalT.isSelected())){	
						if(ev.getStateChange()==ItemEvent.SELECTED){
							periodString = "OT";
						} else if(ev.getStateChange()==ItemEvent.DESELECTED){
							if (period == 1) {
								periodString = "1st";
							} else if (period == 2) {
								periodString = "2nd";
							} else if (period == 3) {
								periodString = "3rd";
							} else if (period <= 20){
								periodString = period+"th";
							} else {
								periodString = period+"";
							}
						}
						updateFile(filePath.getText());
					}	
				}			    	  
				});

				half.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent ev) {
						if (!(finalT.isSelected())){	
							if(ev.getStateChange()==ItemEvent.SELECTED){
								periodString = "Half";
								overtime.setSelected(false);
							} else if(ev.getStateChange()==ItemEvent.DESELECTED){
								if (!(overtime.isSelected())){
									if (period == 1) {
										periodString = "1st";
									} else if (period == 2) {
										periodString = "2nd";
									} else if (period == 3) {
										periodString = "3rd";
									} else if (period <= 20){
										periodString = period+"th";
									} else {
										periodString = period+"";
									}
								}
								else {
									periodString = "OT";
								}
							}
							updateFile(filePath.getText());
						}	
					}			    	  
					});

					freeze.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent ev) {	
							if(ev.getStateChange()==ItemEvent.SELECTED){
								freezeVar = true;
							} else if(ev.getStateChange()==ItemEvent.DESELECTED){
								freezeVar = false;
							}
							updateFile(filePath.getText());
						}				    	  
					});

					finalT.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent ev) {
							if (!(half.isSelected())){	
								if(ev.getStateChange()==ItemEvent.SELECTED){
									periodString = "Final";
									overtime.setSelected(false);
								} else if(ev.getStateChange()==ItemEvent.DESELECTED){
									if (!(overtime.isSelected())){
										if (period == 1) {
											periodString = "1st";
										} else if (period == 2) {
											periodString = "2nd";
										} else if (period == 3) {
											periodString = "3rd";
										} else if (period <= 20){
											periodString = period+"th";
										} else {
											periodString = period+"";
										}
									}
									else {
										periodString = "OT";
									}
								}
								updateFile(filePath.getText());
							}	
						}			    	  
					});
			

			//Reset the controller for a new game
			JButton reset = new JButton ("Reset");
			reset.setBounds(250, 40, 100, 20);
			reset.setBackground(Color.RED);
			reset.setForeground(Color.WHITE);
			frame.add(reset);

			JButton foulReset = new JButton ("Foul Reset");
			foulReset.setBounds(250, 70, 100, 20);
			foulReset.setBackground(Color.RED);
			foulReset.setForeground(Color.WHITE);
			frame.add(foulReset);
			
			reset.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		period = 1;
		    		periodString = "1st";
		    		hscore = 0;
		    		vscore = 0;
		    		hbonus = 0;
		    		vbonus = 0;
		    		hdbonus = 0;
		    		vdbonus = 0;
		    		hfouls = 0;
		    		vfouls = 0;
		    		htol = 5;
		    		vtol = 5;
		    		hposs = 0;
		    		vposs = 0;
		    		timeOn = false;
		    		min = 8;
		    		sec = 0;
					tenth = 0;
		    		clock = "8:00";
		    		homePoss.setSelected(false);
		    		visitorPoss.setSelected(false);
					startTimer.setSelected(false);
		    		updateFile(filePath.getText());
		    	}  
		    });

			foulReset.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		hbonus = 0;
		    		vbonus = 0;
		    		hdbonus = 0;
		    		vdbonus = 0;
		    		hfouls = 0;
		    		vfouls = 0;
		    		updateFile(filePath.getText());
		    	}  
		    });

			JButton plus1second = new JButton ("+1 second");
			JButton minus1second = new JButton ("-1 second");
			JButton plus10seconds = new JButton ("+10 seconds");
			JButton minus10seconds = new JButton ("-10 seconds");
			JButton plustenthsecond = new JButton ("+0.1 second");
			JButton minustenthsecond = new JButton ("-0.1 second");
			
			plus1second.setBounds(380, 400, 100, 20);
			minus1second.setBounds(380, 425, 100, 20);
			plus10seconds.setBounds(380,450, 150, 20);
			minus10seconds.setBounds(380, 475, 150, 20);
			plustenthsecond.setBounds(500, 400, 100, 20);
			minustenthsecond.setBounds(500, 425, 100, 20);
			
			frame.add(plus1second);
			frame.add(minus1second);
			frame.add(plus10seconds);
			frame.add(minus10seconds);
			frame.add(plustenthsecond);
			frame.add(minustenthsecond);
			
			plus1second.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		if ((sec + 1) >= 60) {
		    			sec = (sec + 1) - 60;
		    			min++;
		    		} else {
		    			sec++;
		    		}
		    		updateFile(filePath.getText());
		    		System.out.print(min + " : "+sec);
		    	}  
		    });
			minus1second.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		if ((sec - 1) < 0) {
		    			sec = 60 + (sec - 1);
		    			min--;
		    		} else {
		    			sec--;
		    		}
		    		updateFile(filePath.getText());
		    		System.out.print(min + " : "+sec);
		    	}  
		    });
			plus10seconds.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		if ((sec + 10) >= 60) {
		    			sec = (sec + 10) - 60;
		    			min++;
		    		} else {
		    			sec+=10;
		    		}
		    		updateFile(filePath.getText());
		    		System.out.print(min + " : "+sec);
		    	}  
		    });
			minus10seconds.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		if ((sec - 10) < 0) {
		    			sec = 60 + (sec - 10);
		    			min--;
		    		} else {
		    			sec-=10;
		    		}
		    		updateFile(filePath.getText());
		    		System.out.print(min + " : "+sec);
		    	}  
		    });
			minustenthsecond.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		if ((tenth - 1) < 0) {
		    			tenth = 9;
		    			if ((sec - 1) < 0) {
							sec = 59;
							min--;
						} else {
							sec -= 1;
						}
		    		} else {
		    			tenth-=1;
		    		}
		    		updateFile(filePath.getText());
		    		System.out.print(min + " : "+sec);
		    	}  
		    });
			plustenthsecond.addActionListener(new ActionListener(){  
		    	public void actionPerformed(ActionEvent e){
		    		if ((tenth + 1) > 9) {
		    			tenth = 0;
		    			if ((sec + 1) >= 60) {
							sec = 0;
							min++;
						} else {
							sec += 1;
						}
		    		} else {
		    			tenth+=1;
		    		}
		    		updateFile(filePath.getText());
		    		System.out.print(min + ":"+ sec + "." + tenth + "\n");
		    	}  
		    });

			//Add functionality for vMix input switching
			JLabel ipaddressLabel = new JLabel ("Enter The Web Address IP for vMix:");
			ipaddressLabel.setBounds(500, 10, 250, 20);
			frame.add(ipaddressLabel);
			
			JTextField ipaddress = new JTextField ("Enter IP Address Here");
			ipaddress.setBounds(500, 35, 250, 20);
			frame.add(ipaddress);
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JList inputsList = new JList (inputs.toArray());
		    inputsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		    inputsList.setLayoutOrientation(JList.VERTICAL);
		    inputsList.setVisibleRowCount(-1);
		    JScrollPane scrollPane = new JScrollPane();
		    scrollPane.setViewportView(inputsList);
		    inputsList.setLayoutOrientation(JList.VERTICAL);
		    scrollPane.setBounds(625,60,125,230);
		    frame.add(scrollPane);
		    
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JList previewList = new JList (inputs.toArray());
		    previewList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		    previewList.setLayoutOrientation(JList.VERTICAL);
		    previewList.setVisibleRowCount(-1);
		    JScrollPane previewscrollPane = new JScrollPane();
		    previewscrollPane.setViewportView(previewList);
		    previewList.setLayoutOrientation(JList.VERTICAL);
		    previewscrollPane.setBounds(500,60,125,230);
		    frame.add(previewscrollPane);
			
			JTextField add = new JTextField ("1 - scoreboard");
			add.setBounds(500, 300, 250, 20);
			frame.add(add);
			
			JLabel addLabel = new JLabel ("Add the Inputs in vMix in Order:");
			addLabel.setBounds(500, 275, 250, 20);
			frame.add(addLabel);
			
	        add.addActionListener(new java.awt.event.ActionListener() {
	            @SuppressWarnings("unchecked")
				public void actionPerformed(java.awt.event.ActionEvent e) {
	            	inputs.add(add.getText());
	            	inputsList.setListData(inputs.toArray());
	            	previewList.setListData(inputs.toArray());
	            }
	        });
	        
	        JButton remove = new JButton ("Remove");
	        remove.setBounds(500, 325, 250, 20);
	        frame.add(remove);
	        
	        remove.addActionListener(new java.awt.event.ActionListener() {
	            @SuppressWarnings("unchecked")
				public void actionPerformed(java.awt.event.ActionEvent e) {
	            	inputs.remove(inputsList.getSelectedIndex());
	            	inputsList.setListData(inputs.toArray());
	            	previewList.setListData(inputs.toArray());
	            }
	        });
	        
	        inputsList.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (ipaddress.getText().equals("Enter IP Address Here")) {
	            		JOptionPane.showMessageDialog(new JFrame(), "Please Enter An IP Address");
	            	} else {
	            		try {
	            			String url = ipaddress.getText()+"/API/?Function=Cut&Duration=10&Input="+(inputsList.getSelectedIndex()+1);      
	            			URL obj = new URL(url);
	            			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	            			con.setRequestMethod("GET");
	            			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	            			@SuppressWarnings("unused")
	            			int responseCode = con.getResponseCode();
	            		} catch (ProtocolException e1) {
	            			// TODO Auto-generated catch block
	            			e1.printStackTrace();
	            		}
	            		//add request header
	            		catch (IOException e1) {
	            			// TODO Auto-generated catch block
	            			e1.printStackTrace();
	            		}
	            	}
				}
	        });	
	        
	        previewList.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (ipaddress.getText().equals("Enter IP Address Here")) {
	            		JOptionPane.showMessageDialog(new JFrame(), "Please Enter An IP Address");
	            	} else {
	            		try {
	            			String url = ipaddress.getText()+"/API/?Function=PreviewInput&Input="+(previewList.getSelectedIndex()+1);      
	            			URL obj = new URL(url);
	            			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	            			con.setRequestMethod("GET");
	            			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	            			@SuppressWarnings("unused")
	            			int responseCode = con.getResponseCode();
	            		} catch (ProtocolException e1) {
	            			// TODO Auto-generated catch block
	            			e1.printStackTrace();
	            		}
	            		//add request header
	            		catch (IOException e1) {
	            			// TODO Auto-generated catch block
	            			e1.printStackTrace();
	            		}
	            	}
				}
	        });	
	        
	        JButton previewtoProgram = new JButton ("Preview -> Program");
	        previewtoProgram.setBounds(500, 350, 250, 20);
	        frame.add(previewtoProgram);
	        
	        previewtoProgram.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		            try {
						String url = ipaddress.getText()+"/API/?Function=Cut&Duration=10&Input=0";      
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						con.setRequestMethod("GET");
						con.setRequestProperty("User-Agent", "Mozilla/5.0");
						@SuppressWarnings("unused")
						int responseCode = con.getResponseCode();
					} catch (ProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            //add request header
		            catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
	}

	//Update the output file to send new data to vMix
	public static void updateFile (String filePath) {
		if (!freezeVar) {
			try {
				FileWriter Fwriter = new FileWriter(new File (filePath));
				BufferedWriter writer = new BufferedWriter(Fwriter);
				if (!(half.isSelected() || finalT.isSelected())){
					if (min == 0) {
						clock = String.format("%d.%d", sec, tenth);
					}
					else {
						clock = String.format("%d:%02d", min, sec);
					}
				}
				else {
					clock = "";
				}
				writer.write("[{\"Clock\":\""+clock+ "\", \"Period\":\""+periodString+"\",\"HomeScore\":\""+hscore+ "\",\"VisitorScore\":\""+vscore+"\", \"HomePoss\":\""+hposs+"\", \"VisitorPoss\":\""+vposs+"\", \"HomeBonus\":\""+hbonus+"\", \"HomeDoubleBonus\":\""+hdbonus+"\", \"VisitorBonus\":\""+vbonus+"\", \"VisitorDoubleBonus\":\""+vdbonus+"\", \"HomeFouls\":\""+hfouls+"\", \"VisitorFouls\":\""+vfouls+"\", \"HomeTOL\":\""+htol+"\", \"VisitorTOL\":\""+vtol+"\"}]");
				writer.close();

				clockLabel.setText("Clock: " + clock);
				periodLabel.setText("Period: " + periodString);
				hscoreLabel.setText("Score: " + hscore);
				vscoreLabel.setText("Score: " + vscore);
				homeTOL.setText("TOL: " + htol);
				visitorTOL.setText("TOL: " + vtol);
				hFoulsLabel.setText("Fouls: " + hfouls);
				vFoulsLabel.setText("Fouls: " + vfouls);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
