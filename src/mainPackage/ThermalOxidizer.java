package mainPackage;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ThermalOxidizer extends SwingWorker<Integer, String> {

    private HashMap textFieldHash;
    private JTable table;
    private JPanel tempProfile_panel;
    private JPanel coProfilePanel;
    private JPanel noxProfilePanel;
    private String outputDirectory;
    
    public ThermalOxidizer(HashMap textFieldHash, JTable table, JPanel tempProfile_panel, JPanel coProfile_panel, 
                                                                JPanel noxProfile_panel, String outputDirectory) {
        this.textFieldHash = textFieldHash;
        this.table = table;
        this.tempProfile_panel = tempProfile_panel;
        this.coProfilePanel = coProfile_panel;
        this.noxProfilePanel = noxProfile_panel;
        this.outputDirectory = outputDirectory;
    }
    
    //<editor-fold desc="Variables">
    private double initialTime;
    private double finalTime;
    private double sumMols;
    private double sumNCpGas;
    private double massFlowRateArInAirRing1;
    private double massFlowRateO2InAirRing1;
    private double massFlowRateN2InAirRing1;
    private double massFlowRateH2OInAirRing1;
    private double molFlowN2Syngas;
    private double molFlowO2Syngas;
    private double molFlowArSyngas;
    private double molFlowH2OSyngas;
    private double molFlowCO2Syngas;
    private double molFlowCOSyngas;
    private double molFlowCH4Syngas;
    private double molFlowH2Syngas;
    private double molFlowH2SSyngas;
    private double molFlowHClSyngas;
    private double molFlowC6H6Syngas;
    private double molFlowNH3Syngas;
    private double molFlowNOSyngas;
    private double molFlowSO2Syngas;
    private int reactorLocationInt;
    private double sumNCpInFlueGasRing1, sumNCpInFlueGasRing2,
                    deltaHFlueGasRing1, deltaHFlueGasRing2, sumNCpInAirRing1, sumNCpInAirRing2,
                    sumNCpInSyngas, deltaHSyngas, deltaHAirRing1, deltaHAirRing2, sumNCpInAmmoniaInj,
                    deltaHAmmoniaInj, sumDeltaHRing1B4Combustion, sumDeltaHRing2B4Combustion,
                    sumDeltaHAmmoniaInj, deltaHCombustionRing1, deltaHCombustionRing2,
                    sumDeltaHRing1, sumDeltaHRing2, sumDeltaB4HAmmoniaInj;
    
    private double massFlowRateN2InFlueGasRing1;
    private double massFlowRateArInFlueGasRing1;
    private double massFlowRateO2InFlueGasRing1;
    private double massFlowRateH2OInFlueGasRing1;
    private double massFlowRateCO2InFlueGasRing1;
    private double tempSyngasF, tempSyngasK;
    private double flueMassFlowRateRing1, flueMassFlowRateRing2;
    private double defaultValueN2AirVol, defaultValueO2AirVol,
                    defaultValueArAirVol, massN2InAir, massO2InAir, massArInAir,
                    massH2OInAir;
    private double tempGuessF, tempGuessK, guessHeat = 0.0, sumNCp = 0.0;
    private double cpN2Guess;
    private double cpO2Guess;
    private double cpArGuess;
    private double cpH2OGuess;
    private double cpCO2Guess;
    private double cpCOGuess;
    private double cpCH4Guess;
    private double cpC6H6Guess;
    private double cpH2Guess;
    private double cpHClGuess;
    private double cpH2SlGuess;
    private double cpSO2lGuess;
    private double cpNH3lGuess;
    private double cpNOGuess;
    private double cpN2InSyngas;
    private double cpO2InSyngas;
    private double cpArInSyngas;
    private double cpH2OInSyngas;
    private double cpCO2InSyngas;
    private double cpCOInSyngas;
    private double cpCH4InSyngas;
    private double cpC6H6InSyngas;
    private double cpH2InSyngas;
    private double cpHClInSyngas;
    private double cpH2SInSyngas;
    private double cpSO2InSyngas;
    private double cpNH3InSyngas;
    private double cpNOInSyngas;
    private double outerArea;
    private double airMassFlowRateRing1;
    private double flueGasMassFlowRateRing1;
    private double syngasMassFlowRate;
    private int toLengthInt;
    private double airMassFlowRateRing2;
    private double massFlowRateArInAirRing2;
    private double massFlowRateO2InAirRing2;
    private double massFlowRateN2InAirRing2;
    private double massFlowRateH2OInAirRing2;
    private double flueGasMassFlowRateRing2;
    private double massFlowRateN2InFlueGasRing2;
    private double massFlowRateArInFlueGasRing2;
    private double massFlowRateO2InFlueGasRing2;
    private double massFlowRateH2OInFlueGasRing2;
    private double massFlowRateCO2InFlueGasRing2;
    private double reactorLocation;
    private double toLength;
    private double toDiameter;
    private double toRuns;
    private int toRunsInt;
    private int currentTOrun = 1;
    private int excelArraysIndex = 0;
    private double initialVolumetricFlow;
    private double initialVelocity;
    private double initialDeltaTime;
    private double deltaVolumetricFlow;
    private double u;
    private double deltaTime;
    private double deltaIncrement;
    private double newTempK;
    private String[] massFractions;
    private String readTemp;
    private String[] molecularWeights;
    private Double[] mols;
    private Double[] newMolFractions;
    private String[][] formattedMolFractions;
    private String[][] finalMolFractions;
    private String[] speciesNames;
    private String[] temperaturesArray;
    private Double[] temperaturesArrayDoubleKelvin;
    private Double[] temperaturesArrayDoubleFarenheit;
    private Double[] finalTimesArray;
    private Double[] checkMassFractions;
    private String[] exportDataOfInterest;
    private String[] speciesForExcelExport = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2",
                                                "NH3", "NO", "NO2", "O2", "SO", "SO2"};
    
    private final String[] speciesForModelOutputPPM = {"CO", "HCL", "NH3", "NO", "NO2", "SO2"};
    
    private final String[] speciesForModelOutputPercentage = {"CO2", "H2O", "N2", "O2"}; 
    
    private final String[] speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2",
                                             "NH3", "NO", "NO2", "O2", "SO", "SO2"};
    
    private double ring1dist;
    private double ring2dist;
    private double nh3InjSite;
    private int numSpecies;
    private int numOutputLines;
    private double totalMassFlowRateAfterRing1;
    private double totalMassFlowRateAfterRing2;
    private double totalMassFlowRateAfterAmmoniaInj;
    private double ammoniaInjMassFlow;
    private double molFlowRateSyngas;
    private String fileLine;
    //</editor-fold>
    
    //<editor-fold desc="Final Variable (DO NOT MODIFY)">
    private final double EPSILON = 0.00000001;
    private final double ALPHA_N2 = 6.524, BETA_N2 = 1.25E-03, GAMMA_N2 = -1.00E-09;
    private final double ALPHA_O2 = 6.148, BETA_O2 = 3.10E-03, GAMMA_O2 = -9.23E-07;
    private final double ALPHA_AR = 6.148, BETA_AR = 3.10E-03, GAMMA_AR = -9.23E-07;
    private final double ALPHA_H2O = 7.256, BETA_H2O = 2.30E-03, GAMMA_H2O = 2.83E-07;
    private final double ALPHA_CO2 = 6.214, BETA_CO2 = 1.04E-02, GAMMA_CO2 = -3.55E-06;
    private final double ALPHA_CO = 6.42, BETA_CO = 1.67E-03, GAMMA_CO = -1.96E-07;
    private final double ALPHA_CH4 = 3.381, BETA_CH4 = 1.80E-02, GAMMA_CH4 = -4.30E-06;
    private final double ALPHA_C6H6 = -0.409, BETA_C6H6 = 7.76E-02, GAMMA_C6H6 = -2.64E-05;
    private final double ALPHA_H2 = 6.947, BETA_H2 = -2.00E-04, GAMMA_H2 = 4.81E-07;
    private final double ALPHA_HCL = 6.732, BETA_HCL = 4.33E-04, GAMMA_HCL = 3.70E-07;
    private final double ALPHA_H2S = 6.662, BETA_H2S = 5.13E-03, GAMMA_H2S = -8.54E-07;
    private final double ALPHA_SO2 = 7.116, BETA_SO2 = 9.51E-03, GAMMA_SO2 = 3.51E-06;
    private final double ALPHA_NH3 = 6.086, BETA_NH3 = 8.81E-03, GAMMA_NH3 = -1.51E-06;
    private final double ALPHA_NO = 7.02, BETA_NO = -3.70E-04, GAMMA_NO = 2.55E-06;
    private final double N_MW = 14.007;
    private final double O_MW = 15.999;
    private final double AR_MW = 39.948;
    private final double C_MW = 12.011;
    private final double H_MW = 1.0079;
    private final double S_MW = 32.065;
    private final double CL_MW = 35.453;
    // </editor-fold>>

    // Added 07/06013 from java world
    class StreamGobbler extends Thread {

        InputStream is;
        String type;
        OutputStream os;

        StreamGobbler(InputStream is, String type) {
            this(is, type, null);
        }

        StreamGobbler(InputStream is, String type, OutputStream redirect) {
            this.is = is;
            this.type = type;
            this.os = redirect;
        }

        @Override
        public void run() {
            try {
                PrintWriter pw = null;

                if (os != null) {
                    pw = new PrintWriter(os);
                }

                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    if (pw != null) {
                        pw.println(line);
                    }
                    System.out.println(type + ">" + line);
                }
                if (pw != null) {
                    pw.flush();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace(System.out);
            }
        }
    }

    @Override
    protected Integer doInBackground() throws Exception {    
        
        updateAllFields();

        //verifySynInputs();
        //verifyAirInputs();
        //SolverFilePath needs to be updated to work on any user's 
        //computer by adjusting the file path CHEMKED uses
        setSolverFilePath();

        //Get the number of species 
        getNumSpecies();

        //Get The necessary values to begin calculations
        getValues();

        finalTimesArray = new Double[toRunsInt];
        temperaturesArray = new String[toRunsInt];
        temperaturesArrayDoubleKelvin = new Double[toRunsInt];
        temperaturesArrayDoubleFarenheit = new Double[toRunsInt];

        //Create the array for the formattedMolFractions using the numtoRuns and the number of species
        formattedMolFractions = new String[toRunsInt][numSpecies];
        finalMolFractions = new String[toRunsInt][numSpecies];

        calculateVelocity();
        initialupdateFile();
        grabSpeciesNames();
        getMolecularWeights();
        Scanner scanFile;
        Formatter formatFile;

        String excelSheetFileName = "";
        String excelSheetTabName = "";

        //Counter to keep track of the currentRun
        int currentRunNum = 1;

        int newProgress;

        while (reactorLocation < toLength + deltaIncrement) {

            //if we are at the ring 1 location
            if (Math.abs(reactorLocation - ring1dist) < EPSILON) {

                //Do an energy balance at the first air ring and give back a new temperature
                doEnergyBalance1();
                
                updateFile();

                //Write the updated mol fractions to SOLTMP.txt
                rewriteSOLTMP(true);

            } //if we are at the ring 2 location
            else if (Math.abs(reactorLocation - ring2dist) < EPSILON) {

                //Do an energy balance at the first air ring and give back a new temperature
                doEnergyBalance2();

                updateFile();

                //Write the updated mol fractions to SOLTMP.txt
                rewriteSOLTMP(true);

            } //if we are at the NH3 injection location
            else if (Math.abs(reactorLocation - nh3InjSite) < EPSILON) {

                //Do a mass balance at the ring 3 location
                doMassBalance3();
                
                //Do an energy balance at the first air ring and give back a new temperature
                doEnergyBalance3();

                updateFile();
                
                 //Write the updated mol fractions to SOLTMP.txt
                rewriteSOLTMP(true);

            } else if (reactorLocation > deltaIncrement && reactorLocation != ring1dist && reactorLocation != ring2dist && reactorLocation != nh3InjSite) {

                //Convert the new mass fractions to mol fractions (true=overwrite the mol fractions of previous run for export data)
                convertMassFractionToMol(true);

                //Do an energy balance at the first air ring and give back a new temperature
                doEnergyBalance0();

                calculateVelocity();

                updateFile();

                //Write the updated mol fractions to SOLTMP.txt
                rewriteSOLTMP(true);


            }
            
            try {

                FileOutputStream fos = new FileOutputStream("CHEMKED\\error" + currentRunNum + ".txt");

                String[] CHEMKED = {"CHEMKED\\CHEMKED_SOLVER.exe", "CHEMKED\\SOLTMP.txt"};
                ProcessBuilder builder = new ProcessBuilder(CHEMKED);
                builder.directory(new File("CHEMKED\\"));
                builder.redirectError();
                Process pr = builder.start();
                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                // any error message?
                StreamGobbler errorGobbler = new StreamGobbler(pr.getErrorStream(), "ERROR");

                // any output?
                StreamGobbler outputGobbler = new StreamGobbler(pr.getInputStream(), "OUTPUT", fos);

                // kick them off
                errorGobbler.start();
                outputGobbler.start();

                int exitVal;
                exitVal = pr.waitFor();
                System.out.println("Exited with error code " + exitVal);
                fos.flush();
                fos.close();

            } catch (IOException | InterruptedException e) {
                System.out.println(e.toString());
                e.printStackTrace(System.out);
            }

            //Copy the last part of the SOLTMP (the resulting mass fractions) 
            //into a shorter file "Output_i", where currentRunNum is the current run
            shortenSOLTMP(currentRunNum);

            //Save a copy of the SOLTMP for each run
            copyCurrentRunSOLTMP(currentRunNum);

            getNumOutputLines(currentRunNum);

            //Get the resulting mass fractions
            getMassFractions(currentRunNum);

            //Convert the mass fractions to moles
            convertMassFractionToMol(false);

            // update progress bar
            reactorLocationInt = (int) reactorLocation;

            newProgress = (reactorLocationInt * 100) / toLengthInt;
            setProgress(newProgress);

            //Increment the delta and calculate the gas velocity
            reactorLocation += deltaIncrement;

            try {

                scanFile = new Scanner(new FileReader("CHEMKED\\temp-SOLTMP.txt"));

                formatFile = new Formatter(new File("CHEMKED\\SOLTMP.txt"));

                int i = 0;

                while (scanFile.hasNextLine()) {
                    fileLine = scanFile.nextLine();
                    // isothermal = 0, adiabatic = 1
                    if (i == 14) {
                        fileLine = " " + 0 + " !Temperature Equation";
                    }

                    formatFile.format("%s%n", fileLine);

                    if (fileLine.matches(" THERMO")) {
                        break;
                    }

                    i++;
                }

                //Print the remaining lines
                while (scanFile.hasNextLine()) {
                    fileLine = scanFile.nextLine();
                    formatFile.format("%s%n", fileLine);
                }

                scanFile.close();
                formatFile.close();

            } catch (Exception e) {
                e.printStackTrace(System.out);
            }

            //Update the SOLTMP file with the intial time, final time, and temp
            updateFile();

            //Rewrite the SOLTMP file with the new mass fractions in moles
            rewriteSOLTMP(false);

            //Export the mass fractions in moles to an Excel sheet
            excelSheetFileName = "ExcelOutput" + currentRunNum;

            currentRunNum++;
            currentTOrun++;

        }

        for (int outputNum = 1; outputNum <= 40; outputNum++) {

            grabFinalOutputs(outputNum);

        }

        try {
            exportData(outputDirectory, excelSheetTabName, finalMolFractions);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThermalOxidizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ThermalOxidizer.class.getName()).log(Level.SEVERE, null, ex);
        }

        publish(finalMolFractions[0]);

        //System.out.println("Program has successfully completed");

        return 0;

    }

    @Override
    protected void done() {
        fillTable();
        drawTemperatureProfile();
        drawCOProfile();
        drawNOxProfile();
    }

    private void fillTable() {
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);
        Object[] tableHeader = {"Species", "Outlet Concentration"};
        model.addRow(tableHeader);
        
        DecimalFormat df = new DecimalFormat("0.0");
        
        for(int x=0; x < speciesForModelOutputPercentage.length; x++){
            
            for(int i=0; i < speciesForExcelExport.length; i++){
                
                if(speciesForModelOutputPercentage[x].matches(speciesForExcelExport[i])){
                        
                        double dataDouble = Double.parseDouble(exportDataOfInterest[i]);
                        dataDouble = dataDouble*100;
                        Object[] rowData = {speciesForModelOutputPercentage[x], df.format(dataDouble) + "%"};
                        model.addRow(rowData);
                        break;
                        
                } 
                
            }
            
        }
        
        for(int y=0; y < speciesForModelOutputPPM.length; y++){
            
            for(int i=0; i < speciesForExcelExport.length; i++){
                
                if(speciesForModelOutputPPM[y].matches(speciesForExcelExport[i])){
                    
                    double dataDouble = Double.parseDouble(exportDataOfInterest[i]);
                    dataDouble = dataDouble*1000000;
                    Object[] rowData = {speciesForModelOutputPPM[y], df.format(dataDouble) + " ppm"};
                    model.addRow(rowData);
                    break;
                    
                }
                
            }
            
        }
    
    }

    private void drawTemperatureProfile() {

        final XYSeries series1 = new XYSeries("First");

        for (int k = 0; k < temperaturesArrayDoubleFarenheit.length; k++) {
            series1.add((k + 1), temperaturesArrayDoubleFarenheit[k]);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(series1);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Temperature Profile", "Distance", "Temperature(F)", dataset, PlotOrientation.VERTICAL, false, true, false);

        chart.setBorderPaint(Color.WHITE);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setSize(tempProfile_panel.getSize());
        tempProfile_panel.add(chartPanel);

    }
    
    private void drawCOProfile() {
        
        int indexOfCO = 0;
        
        final XYSeries series1 = new XYSeries("First");
        
        for(int x =0; x<speciesNames.length; x++){
            if(speciesNames[x].matches("CO")){
                indexOfCO = x;
                break;
            }
        }
        
        for(int y=0; y < finalMolFractions.length; y++){
            Double co_value = Double.parseDouble(finalMolFractions[y][indexOfCO]);
            co_value = co_value * 1000000;
            series1.add((y + 1), co_value);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(series1);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "CO Profile", "Distance", "Concentration(ppm)", dataset, PlotOrientation.VERTICAL, false, true, false);

        chart.setBorderPaint(Color.WHITE);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setSize(coProfilePanel.getSize());
        coProfilePanel.add(chartPanel);

    }
    
    private void drawNOxProfile() {
        
        int indexOfNO = 0;
        int indexOfNO2 = 0;
        
        final XYSeries series1 = new XYSeries("NO");
        final XYSeries series2 = new XYSeries("NO2");
        final XYSeries series3 = new XYSeries("NOx");
        
        for(int x =0; x<speciesNames.length; x++){
            if(speciesNames[x].matches("NO")){
                indexOfNO = x;
            }
            if(speciesNames[x].matches("NO2")){
                indexOfNO2 = x;
                break;
            }
        }
        
        for(int y=0; y < finalMolFractions.length; y++){
            
            Double no_value = Double.parseDouble(finalMolFractions[y][indexOfNO]);
            no_value = no_value * 1000000;
            series1.add((y + 1), no_value);
            
            Double no2_value = Double.parseDouble(finalMolFractions[y][indexOfNO2]);
            no2_value = no2_value * 1000000;
            series2.add((y + 1), no2_value);
            
            Double nox_value = no_value + no2_value;
            series3.add((y + 1), nox_value);
            
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();

        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "NOx Profile", "Distance", "Concentration(ppm)", dataset, PlotOrientation.VERTICAL, true, true, false);

        chart.setBorderPaint(Color.WHITE);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(2, Color.DARK_GRAY);
        //renderer.setSeriesShapesVisible(0, false);
        //renderer.setSeriesShapesVisible(1, false);
        //renderer.setSeriesShapesVisible(2, false);
        
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setSize(noxProfilePanel.getSize());
        noxProfilePanel.add(chartPanel);

    }

    private void initialupdateFile() {

        Scanner scanFile;
        Formatter formatFile;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\BigFile.txt"));

            formatFile = new Formatter(new File("CHEMKED\\SOLTMP.txt"));

            String[] parts;
            String lastWord;
            String[] currentSpecies = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "O2"};
            int speciesIndex = 0;
            int currentSpeciesLenth = currentSpecies.length;
            String tfText = "";
            int i = 0;

            // 
            while (scanFile.hasNextLine()) {

                fileLine = scanFile.nextLine();

                if (i == 15) {
                    fileLine = " " + tempSyngasK + " !Initial Temperature";
                }

                if (i == 18) {
                    fileLine = " " + initialTime + " !Initial Time";
                }

                if (i == 19) {
                    fileLine = " " + finalTime + " !Final Time";
                }
                formatFile.format("%s%n", fileLine);

                if (fileLine.matches(" THERMO")) {
                    break;
                }

                i++;
            }

            while (scanFile.hasNextLine() && (speciesIndex < currentSpeciesLenth)) {

                fileLine = scanFile.nextLine();

                if (fileLine.contains(" " + currentSpecies[speciesIndex] + " ")) {

                    //find the last word/number in the sentence
                    parts = fileLine.split(" ");

                    lastWord = parts[parts.length - 1];

                    //find the index of that last word
                    int lastIndex = fileLine.lastIndexOf(lastWord);

                    //make a new line that has substring up to the index of the last word
                    String newLine = fileLine.substring(0, lastIndex);

                    //switch statememnt to select what textfield content is copied to the file
                    switch (speciesIndex) {
                        case 0:
                            tfText = ((String) textFieldHash.get("ARsyn"));
                            break;
                        case 1:
                            tfText = ((String) textFieldHash.get("C6H6syn"));
                            break;
                        case 2:
                            tfText = ((String) textFieldHash.get("CH4syn"));
                            break;
                        case 3:
                            tfText = ((String) textFieldHash.get("COsyn"));
                            break;
                        case 4:
                            tfText = ((String) textFieldHash.get("CO2syn"));
                            break;
                        case 5:
                            tfText = ((String) textFieldHash.get("H2syn"));
                            break;
                        case 6:
                            tfText = ((String) textFieldHash.get("H2Osyn"));
                            break;
                        case 7:
                            tfText = ((String) textFieldHash.get("H2Ssyn"));
                            break;
                        case 8:
                            tfText = ((String) textFieldHash.get("HCLsyn"));
                            break;
                        case 9:
                            tfText = ((String) textFieldHash.get("N2syn"));
                            break;
                        case 10:
                            tfText = ((String) textFieldHash.get("NH3syn"));
                            break;
                        case 11:
                            tfText = ((String) textFieldHash.get("O2syn"));
                            break;
                        default:
                            break;
                    }

                    //Add to that string the new updated value
                    newLine = newLine.concat(tfText);

                    formatFile.format("%s%n", newLine);

                    speciesIndex++;

                } else {
                    //Update the file
                    formatFile.format("%s%n", fileLine);
                }

            }

            //Print the remaining lines
            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();
                formatFile.format("%s%n", fileLine);
            }

            scanFile.close();
            formatFile.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

    }

    private void updateFile() {

        Scanner copyFile1;
        Formatter copyFile2;
        Scanner scanFile;
        Formatter formatFile;

        String copyLine;

        try {

            copyFile1 = new Scanner(new FileReader("CHEMKED\\SOLTMP.txt"));
            copyFile2 = new Formatter(new File("CHEMKED\\temp-SOLTMP.txt"));

            //copy contents of SOLTMP into temp-SOLTMP
            while (true) {
                if (copyFile1.hasNextLine()) {
                    copyLine = copyFile1.nextLine();
                    copyFile2.format("%s%n", copyLine);
                } else {
                    break;
                }
            }

            copyFile1.close();
            copyFile2.close();

            scanFile = new Scanner(new FileReader("CHEMKED\\temp-SOLTMP.txt"));

            formatFile = new Formatter(new File("CHEMKED\\SOLTMP.txt"));

            int i = 0;

            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();

                if (i == 15) {
                    fileLine = " " + readTemp + " !Initial Temperature";
                }

                if (i == 18) {
                    fileLine = " " + initialTime + " !Initial Time";
                }

                if (i == 19) {
                    fileLine = " " + finalTime + " !Final Time";
                }
                formatFile.format("%s%n", fileLine);

                if (fileLine.matches(" THERMO")) {
                    break;
                }

                i++;
            }

            //Print the remaining lines
            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();
                formatFile.format("%s%n", fileLine);
            }

            scanFile.close();
            formatFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private void getMassFractions(int currentRun) {

        Scanner changeConcentrationsFile;
        String disposableString = "";
        massFractions = new String[numSpecies];

        try {

            changeConcentrationsFile = new Scanner(new FileReader("CHEMKED\\output_" + currentRun + ".txt"));

            while (changeConcentrationsFile.hasNext()) {
                fileLine = changeConcentrationsFile.next();
                if (fileLine.matches(numOutputLines + "")) {
                    break;
                }
            }

            disposableString = changeConcentrationsFile.next();

            readTemp = changeConcentrationsFile.next();

            if (currentRun < (toRunsInt + deltaIncrement)) {
                temperaturesArray[currentRun - 1] = readTemp;
            }

            int arrayIndex = 0;

            String line;

            while (arrayIndex < numSpecies) {

                if (changeConcentrationsFile.hasNext()) {
                    line = changeConcentrationsFile.next();
                    massFractions[arrayIndex] = line;
                }

                checkMassFractions = new Double[numSpecies];
                checkMassFractions[arrayIndex] = Double.parseDouble(massFractions[arrayIndex]);

                // added 08/08/2013
                if (checkMassFractions[arrayIndex] < 0.00) {

                    massFractions[arrayIndex] = "0.000";
                }
                arrayIndex++;

            }

            changeConcentrationsFile.close();

        } catch (FileNotFoundException | NumberFormatException e) {
            e.printStackTrace(System.out);
        }

    }

    private void convertMassFractionToMol(boolean injectionLocation) {

        mols = new Double[numSpecies];
        Double[] DoubleMW;
        Double[] DoubleNewValues;

        DoubleMW = new Double[numSpecies];
        DoubleNewValues = new Double[numSpecies];

        int index = 0;

        while (index < numSpecies) {
            DoubleMW[index] = Double.parseDouble(molecularWeights[index]);
            DoubleNewValues[index] = Double.parseDouble(massFractions[index]);
            index++;
        }

        newMolFractions = new Double[numSpecies];

        int counter = 0;

        sumMols = 0.0;

        while (counter < numSpecies) {
            mols[counter] = DoubleNewValues[counter] / DoubleMW[counter];
            sumMols += mols[counter];
            counter++;
        }

        int m = 0;

        DecimalFormat df2 = new DecimalFormat("0.000E00");

        while (m < numSpecies) {
            newMolFractions[m] = (DoubleNewValues[m] / DoubleMW[m]) / sumMols;

            //If we are at an injection location we want to overwrite the previous entry so we do [currentTOrun-2] index
            if (injectionLocation) {
                formattedMolFractions[currentTOrun - 2][m] = df2.format(newMolFractions[m]);
            } //otherwise continue as usual
            else {
                formattedMolFractions[currentTOrun - 1][m] = df2.format(newMolFractions[m]);
            }
            m++;
        }

    }

    private void rewriteSOLTMP(boolean injectionLocation) {

        Scanner copyFile1;
        Formatter copyFile2;
        String copyLine;

        try {

            copyFile1 = new Scanner(new FileReader("CHEMKED\\SOLTMP.txt"));

            copyFile2 = new Formatter(new File("CHEMKED\\temp-SOLTMP.txt"));

            //copy contents of SOLTMP into temp-SOLTMP
            while (true) {
                if (copyFile1.hasNextLine()) {
                    copyLine = copyFile1.nextLine();
                    copyFile2.format("%s%n", copyLine);
                } else {
                    break;
                }
            }

            copyFile1.close();
            copyFile2.close();

            Scanner tempFile;
            Formatter newSOLTMPFile;
            String tempFileLine;

            tempFile = new Scanner(new FileReader("CHEMKED\\temp-SOLTMP.txt"));

            newSOLTMPFile = new Formatter(new File("CHEMKED\\SOLTMP.txt"));

            while (tempFile.hasNextLine()) {
                tempFileLine = tempFile.nextLine();
                newSOLTMPFile.format("%s%n", tempFileLine);
                if (tempFileLine.matches(" THERMO")) {
                    break;
                }
            }

            String[] parts;
            String lastWord;
            int currentLine = 1;

            while (currentLine < (numSpecies + 1)) {

                if (tempFile.hasNextLine()) {

                    tempFileLine = tempFile.nextLine();

                    if (tempFileLine.startsWith(" " + currentLine + " ")) {

                        //find the last word/number in the sentence 
                        parts = tempFileLine.split(" ");
                        lastWord = parts[parts.length - 1];

                        //find the index of that last word 
                        int lastIndex = tempFileLine.lastIndexOf(lastWord);

                        //make a new line that has substring up to the index of the last word 
                        String newLine = tempFileLine.substring(0, lastIndex);

                        //Add to that string the new updated value 
                        if (injectionLocation) {
                            newLine = newLine.concat(formattedMolFractions[currentTOrun - 2][currentLine - 1] + "");
                        } else {
                            newLine = newLine.concat(formattedMolFractions[currentTOrun - 1][currentLine - 1] + "");
                        }
                        //Update the file 
                        newSOLTMPFile.format("%s%n", newLine);

                        currentLine++;

                    } else {
                        newSOLTMPFile.format("%s%n", tempFileLine);
                    }

                }

            }

            //Print the remaining lines 
            while (tempFile.hasNextLine()) {
                tempFileLine = tempFile.nextLine();
                newSOLTMPFile.format("%s%n", tempFileLine);
            }


            tempFile.close();

            newSOLTMPFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private void getMolecularWeights() {

        Scanner scanFile;
        String scanLine = null;
        String lastWord;
        String[] parts;
        molecularWeights = new String[numSpecies];
        int currentSpecies = 1;
        int loopCounter = 0;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\SOLTMP.txt"));

            while (scanFile.hasNextLine()) {
                scanLine = scanFile.nextLine();
                if (scanLine.matches(" THERMO")) {
                    break;
                }
            }

            while (currentSpecies < (numSpecies + 1)) {

                while (true) {

                    if (scanFile.hasNextLine()) {
                        scanLine = scanFile.nextLine();
                        loopCounter++;
                    } else {
                        System.out.println("In getMolecularWeights(): next line not found");
                    }

                    if (loopCounter == 4) {
                        if(scanLine != null){
                            parts = scanLine.split(" ");
                            lastWord = parts[parts.length - 1];

                            molecularWeights[currentSpecies - 1] = lastWord;
                            break;
                        }
                    }
                }
                loopCounter = 0;
                currentSpecies++;
            }
            scanFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


    }

    private void calculateVelocity() {

        double R = 0.7302413, insideArea; //ft3 atm R^−1 lb-mol^−1

        insideArea = (Math.PI * Math.pow(toDiameter, 2)) / 4.0;

        if (reactorLocation == deltaIncrement) {

            initialVolumetricFlow = (molFlowRateSyngas * R * tempSyngasK * 9 / 5.) / (1 * 60 * 60); // PV = nRT Gas Law

            initialVelocity = initialVolumetricFlow / insideArea;

            initialDeltaTime = deltaIncrement / initialVelocity;

            initialTime = 0.0;
            finalTime = initialDeltaTime;

        }

        if (reactorLocation > deltaIncrement && reactorLocation < ring1dist) {

            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (molFlowRateSyngas * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

        if (reactorLocation >= ring1dist && reactorLocation < ring2dist) {
            System.out.println("After Ring 1:" + reactorLocation);
            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (sumMols * totalMassFlowRateAfterRing1 * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

        if (reactorLocation >= ring2dist && reactorLocation < nh3InjSite) {
            System.out.println("After Ring 2: " + reactorLocation);
            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (sumMols * totalMassFlowRateAfterRing2 * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

        if (reactorLocation >= nh3InjSite) {
            System.out.println("After NH3 Inj: " + reactorLocation);
            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (sumMols * totalMassFlowRateAfterAmmoniaInj * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }


        if (excelArraysIndex < toRunsInt) {
            finalTimesArray[excelArraysIndex] = finalTime;
            excelArraysIndex++;
        }

    }

    private void calculateVelocityMock() {

        double R = 0.7302413; //Wikipedia ft3 atm R^−1 lb-mol^−1
        double insideArea;

        insideArea = (Math.PI * Math.pow(toDiameter, 2)) / 4.0;

        if (reactorLocation == deltaIncrement) {

            initialVolumetricFlow = (molFlowRateSyngas * R * tempSyngasK * 9 / 5.) / (1 * 60 * 60); // PV = nRT Gas Law

            initialVelocity = initialVolumetricFlow / insideArea;

            initialDeltaTime = deltaIncrement / initialVelocity;

            initialTime = 0.0;
            finalTime = initialDeltaTime;

        }

        if (reactorLocation > deltaIncrement && reactorLocation < ring1dist) {

            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (molFlowRateSyngas * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

        if (reactorLocation >= ring1dist && reactorLocation < ring2dist) {
            System.out.println("After Ring 1:" + reactorLocation);
            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (sumMols * totalMassFlowRateAfterRing1 * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

        if (reactorLocation >= ring2dist && reactorLocation < nh3InjSite) {

            System.out.println("After Ring 2: " + reactorLocation);
            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (sumMols * totalMassFlowRateAfterRing2 * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

        if (reactorLocation >= nh3InjSite) {
            System.out.println("After NH3 Inj: " + reactorLocation);
            initialTime = finalTime;

            newTempK = Double.parseDouble(readTemp);

            deltaVolumetricFlow = (sumMols * totalMassFlowRateAfterAmmoniaInj * R * newTempK * 9.0 / 5) / (1 * 60 * 60); // PQ = nRT Gas Law

            u = deltaVolumetricFlow / insideArea;

            deltaTime = deltaIncrement / u;
            finalTime += deltaTime;
        }

    }

    public void exportData(String ouptutDirectory, String tabName, String[][] data) throws FileNotFoundException, IOException {

        int currIndex1 = 0;
        int currIndex2;
        int numOfInterest = speciesForExcelExport.length;

        exportDataOfInterest = new String[numOfInterest];

        double location1 = deltaIncrement;

        FileOutputStream fileOut;
        Workbook wb = new HSSFWorkbook();
        //Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper;
        createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("Output sheet");

        // Create a row and put some cells in it. Rows are 0 based.
        Row row0 = sheet.createRow((short) 0);
        Cell cell0 = row0.createCell(currIndex1);
        cell0.setCellValue(createHelper.createRichTextString("Location(ft)"));

        cell0 = row0.createCell(currIndex1 + 1);
        cell0.setCellValue(createHelper.createRichTextString("Time(s)"));

        cell0 = row0.createCell(currIndex1 + 2);
        cell0.setCellValue(createHelper.createRichTextString("Temperature(K)"));


        cell0 = row0.createCell(currIndex1 + 3);
        cell0.setCellValue(createHelper.createRichTextString("Temperature(F)"));

        for (int c = 0; c < numSpecies; c++) {
            if (speciesNames[c].matches(speciesForExcelExport[currIndex1])) {
                cell0 = row0.createCell(currIndex1 + 4);
                cell0.setCellValue(createHelper.createRichTextString(speciesNames[c]));
                currIndex1++;
                if (currIndex1 == numOfInterest) {
                    break;
                }
            }
        }

        for (int r = 1; r <= toRunsInt; r++) {

            currIndex2 = 0;

            Row row = sheet.createRow((short) r);

            Cell cell = row.createCell(currIndex2);
            cell.setCellValue(location1);
            location1 += deltaIncrement;

            cell = row.createCell(currIndex2 + 1);

            DecimalFormat df = new DecimalFormat("0.00");
            DecimalFormat df2 = new DecimalFormat("0");
            cell.setCellValue(createHelper.createRichTextString(df.format(finalTimesArray[r - 1]) + ""));

            for (int c = 0; c < toRunsInt; c++) {

                temperaturesArrayDoubleKelvin[c] = Double.parseDouble(temperaturesArray[c]);
                temperaturesArrayDoubleFarenheit[c] = 9 / 5. * temperaturesArrayDoubleKelvin[c] - 459.67;
            }

            cell = row.createCell(currIndex2 + 2);
            cell.setCellValue(createHelper.createRichTextString(df2.format(temperaturesArrayDoubleKelvin[r - 1]) + ""));

            cell = row.createCell(currIndex2 + 3);
            cell.setCellValue(createHelper.createRichTextString(df2.format(temperaturesArrayDoubleFarenheit[r - 1]) + ""));

            for (int c = 0; c < numSpecies; c++) {

                if (speciesNames[c].matches(speciesForExcelExport[currIndex2])) {
                    cell = row.createCell(currIndex2 + 4);
                    cell.setCellValue(createHelper.createRichTextString(data[r - 1][c]));

                    if (r == toRunsInt) {
                        exportDataOfInterest[currIndex2] = data[r - 1][c];
                    }

                    currIndex2++;
                    if (currIndex2 == numOfInterest) {
                        break;
                    }

                }

            }

        }

        // Write the output to a file
        try {
            fileOut = new FileOutputStream(outputDirectory + "/model output.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

    }

    private void updateAllFields() {

        defaultValueN2AirVol = 0.78;
        defaultValueO2AirVol = 0.21;
        defaultValueArAirVol = 0.01;

        double humidityInAirMass = Double.parseDouble((String) textFieldHash.get("H2OairByMass"));
        double sumMolFractionTimeMWAir = N_MW * 2 * defaultValueN2AirVol
                + O_MW * 2 * defaultValueO2AirVol
                + AR_MW * defaultValueArAirVol;


        massN2InAir = (1.0 - humidityInAirMass)
                * (N_MW * 2 * defaultValueN2AirVol) / sumMolFractionTimeMWAir;
        massO2InAir = (1.0 - humidityInAirMass)
                * (O_MW * 2 * defaultValueO2AirVol) / sumMolFractionTimeMWAir;
        massArInAir = (1.0 - humidityInAirMass)
                * (AR_MW * defaultValueArAirVol) / sumMolFractionTimeMWAir;
        massH2OInAir = humidityInAirMass;

        double volumeN2InAir = massN2InAir / (N_MW * 2),
                volumeO2InAir = massO2InAir / (O_MW * 2),
                volumeArInAir = massArInAir / (AR_MW),
                volumeH2OInAir = massH2OInAir / (2 * H_MW + O_MW),
                sumMolsDividedByMW = volumeN2InAir + volumeO2InAir
                + volumeArInAir + volumeH2OInAir,
                molFractionOfN2InAir = volumeN2InAir / sumMolsDividedByMW,
                molFractionOfO2InAir = volumeO2InAir / sumMolsDividedByMW,
                molFractionOfArInAir = volumeArInAir / sumMolsDividedByMW,
                molFractionOfH2OInAir = volumeH2OInAir / sumMolsDividedByMW;


    }

    private void grabSpeciesNames() {

        Scanner bigFile;

        try {

            bigFile = new Scanner(new FileReader("CHEMKED\\BigFile.txt"));

            while (true) {

                if (bigFile.hasNextLine()) {
                    fileLine = bigFile.nextLine();
                }

                //Find the starting point for our shortened file...
                //the "$" sign is special in regex, we need to escape it using the double forward slashes "\\"
                if (fileLine.matches(" !\\$Results  Mass Fractions")) {
                    break;
                }

            }

            fileLine = bigFile.next();
            fileLine = bigFile.next();
            fileLine = bigFile.next();

            speciesNames = new String[numSpecies];

            for (int j = 0; j < numSpecies; j++) {
                if (bigFile.hasNext()) {
                    speciesNames[j] = bigFile.next();
                } else {
                    System.out.println("Error grabbing species name from Big File");
                }
            }

            bigFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private void setSolverFilePath() {

        File file = new File("CHEMKED\\SOLTMP.txt");
        String absolutePath = file.getAbsolutePath();

        Formatter solverFilePathFile;

        try {
            solverFilePathFile = new Formatter(new File("CHEMKED\\SolverFilePath.txt"));
            solverFilePathFile.format("%s%n", absolutePath);
            solverFilePathFile.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

    }

    private void shortenSOLTMP(int currentRun) {

        Scanner scanFile;
        Formatter formatFile;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\SOLTMP.txt"));

            formatFile = new Formatter(new File("CHEMKED\\output_" + currentRun + ".txt"));

            //While loop that goes to the bottom of the file where we want to get the data
            while (true) {

                if (scanFile.hasNextLine()) {
                    fileLine = scanFile.nextLine();
                }

                //Find the starting point for our shortened file...
                //the "$" sign is special in regex, we need to escape it using the double forward slashes "\\"
                if (fileLine.matches(" !\\$Results  Mass Fractions")) {
                    break;
                }
            }

            //Copy the data past the cutoff point into the shortend file
            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();
                formatFile.format("%s%n", fileLine);
            }

            scanFile.close();
            formatFile.close();

        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

    }

    private void copyCurrentRunSOLTMP(int currentRun) {

        Scanner scanFile;
        Formatter formatFile;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\SOLTMP.txt"));

            formatFile = new Formatter(new File("CHEMKED\\outputFile_" + currentRun + ".txt"));

            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();
                formatFile.format("%s%n", fileLine);
            }

            scanFile.close();
            formatFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private void getNumOutputLines(int currentRun) {

        Scanner scanFile;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\output_" + currentRun + ".txt"));

            while (true) {

                if (scanFile.hasNextLine()) {
                    fileLine = scanFile.nextLine();
                }

                if (fileLine.startsWith("       NUMBER OF OUTPUT LINES =")) {
                    break;
                }
            }

            String[] numLinesArray;
            numLinesArray = fileLine.split("\\s+");

            numOutputLines = Integer.parseInt(numLinesArray[numLinesArray.length - 1]);

            scanFile.close();


        } catch (FileNotFoundException | NumberFormatException e) {
            e.printStackTrace(System.out);
        }


    }

    private void getNumSpecies() {

        Scanner readNumSpecies;

        try {

            readNumSpecies = new Scanner(new FileReader("CHEMKED\\BigFile.txt"));

            int m = 0;
            while (readNumSpecies.hasNextLine()) {
                fileLine = readNumSpecies.nextLine();
                if (m == 8) {
                    numSpecies = Integer.parseInt(readNumSpecies.next());
                    break;
                }
                m++;
            }

            readNumSpecies.close();

        } catch (FileNotFoundException | NumberFormatException ex) {
            ex.printStackTrace(System.out);
        }

    }

    private void doMassBalance1() {

        int speciesIndex;
        int preAirIndex = 0;

        double massValue;
        double arraySum = 0.0;
        double[] massValuesArray = new double[numSpecies];

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * syngasMassFlowRate);

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        massValue += massFlowRateArInAirRing1;
                        massValue += massFlowRateArInFlueGasRing1;
                        break;

                    case 4:
                        massValue += massFlowRateCO2InFlueGasRing1;
                        break;

                    case 6:
                        massValue += massFlowRateH2OInAirRing1;
                        massValue += massFlowRateH2OInFlueGasRing1;
                        break;

                    case 9:
                        massValue += massFlowRateN2InAirRing1;
                        massValue += massFlowRateN2InFlueGasRing1;
                        break;

                    case 13:
                        massValue += massFlowRateO2InAirRing1;
                        massValue += massFlowRateO2InFlueGasRing1;
                        break;

                    default:
                        break;
                }

                preAirIndex++;

            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;

        }

        //Convert back to mass fraction and store in mass fraction array
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

    }

    private void doMassBalance2() {

        int speciesIndex;
        int preAirIndex = 0;

        double massValue;
        double arraySum = 0.0;
        double[] massValuesArray = new double[numSpecies];

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex])
                    * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length)
                    && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        massValue += massFlowRateArInAirRing2;
                        massValue += massFlowRateArInFlueGasRing2;
                        break;

                    case 4:
                        massValue += massFlowRateCO2InFlueGasRing2;
                        break;

                    case 6:
                        massValue += massFlowRateH2OInAirRing2;
                        massValue += massFlowRateH2OInFlueGasRing2;
                        break;

                    case 9:
                        massValue += massFlowRateN2InAirRing2;
                        massValue += massFlowRateN2InFlueGasRing2;
                        break;

                    case 13:
                        massValue += massFlowRateO2InAirRing2;
                        massValue += massFlowRateO2InFlueGasRing2;
                        break;

                    default:
                        break;
                }

                preAirIndex++;

            }

            massValuesArray[speciesIndex] = massValue;
            //massFractions[speciesNamesIndex] = massValue+"";       
            arraySum += massValue;

        }

        //Convert back to mass fraction and store in mass fraction array
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

    }

    private void doMassBalance3() {

        int speciesIndex;
        int preAirIndex = 0;

        double massValue;
        double arraySum = 0.0;
        double[] massValuesArray = new double[numSpecies];

        double ammoniaInjH2OMass = Double.parseDouble((String) textFieldHash.get("ammoniaInjectH2O"));
        double ammoniaInjNH3Mass = Double.parseDouble((String) textFieldHash.get("ammoniaInjectNH3"));

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex])
                    * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 6:
                        massValue += ammoniaInjMassFlow * ammoniaInjH2OMass;
                        break;

                    case 10:
                        massValue += ammoniaInjMassFlow * ammoniaInjNH3Mass;
                        break;

                    default:
                        break;
                }

                preAirIndex++;

            }

            massValuesArray[speciesIndex] = massValue;
            //massFractions[speciesNamesIndex] = massValue+"";       
            arraySum += massValue;

        }

        //Convert back to mass fraction and store in mass fraction array
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

    }

    private void doEnergyBalance0() {

        double massValue, massFlowValue;

        massFlowValue = syngasMassFlowRate;

        //if we are at the ring 1 location
        if (Math.abs(reactorLocation - ring1dist) > EPSILON
                && Math.abs(reactorLocation - ring2dist) < EPSILON + deltaIncrement) {
            massFlowValue = totalMassFlowRateAfterRing1;
        } //if we are at the ring 2 location
        else if (Math.abs(reactorLocation - ring2dist) > EPSILON
                && Math.abs(reactorLocation - nh3InjSite) < EPSILON + deltaIncrement) {
            massFlowValue = totalMassFlowRateAfterRing2;
        } //if we are at the NH3 injection location
        else if (Math.abs(reactorLocation - nh3InjSite) > EPSILON) {
            massFlowValue = totalMassFlowRateAfterAmmoniaInj;
        }

        newTempK = Double.parseDouble(readTemp);
        double tempSyn = newTempK - 298, tempSyn2 = newTempK * newTempK - 298 * 298,
                tempSyn3 = newTempK * newTempK * newTempK - 298 * 298 * 298;

        cpN2InSyngas = (ALPHA_N2 * tempSyn + (BETA_N2) / 2 * tempSyn2 + (GAMMA_N2) / 3 * tempSyn3) / tempSyn;
        cpO2InSyngas = (ALPHA_O2 * tempSyn + (BETA_O2) / 2 * tempSyn2 + GAMMA_O2 / 3 * tempSyn3) / tempSyn;
        cpArInSyngas = (ALPHA_AR * tempSyn + (BETA_AR) / 2 * tempSyn2 + GAMMA_AR / 3 * tempSyn3) / tempSyn;
        cpH2OInSyngas = (ALPHA_H2O * tempSyn + (BETA_H2O) / 2 * tempSyn2 + GAMMA_H2O / 3 * tempSyn3) / tempSyn;
        cpCO2InSyngas = (ALPHA_CO2 * tempSyn + (BETA_CO2) / 2 * tempSyn2 + GAMMA_CO2 / 3 * tempSyn3) / tempSyn;
        cpCOInSyngas = (ALPHA_CO * tempSyn + (BETA_CO) / 2 * tempSyn2 + GAMMA_CO / 3 * tempSyn3) / tempSyn;
        cpCH4InSyngas = (ALPHA_CH4 * tempSyn + (BETA_CH4) / 2 * tempSyn2 + GAMMA_CH4 / 3 * tempSyn3) / tempSyn;
        cpC6H6InSyngas = (ALPHA_C6H6 * tempSyn + (BETA_C6H6) / 2 * tempSyn2 + GAMMA_C6H6 / 3 * tempSyn3) / tempSyn;
        cpH2InSyngas = (ALPHA_H2 * tempSyn + (BETA_H2) / 2 * tempSyn2 + GAMMA_H2 / 3 * tempSyn3) / tempSyn;
        cpHClInSyngas = (ALPHA_HCL * tempSyn + (BETA_HCL) / 2 * tempSyn2 + GAMMA_HCL / 3 * tempSyn3) / tempSyn;
        cpH2SInSyngas = (ALPHA_H2S * tempSyn + (BETA_H2S) / 2 * tempSyn2 + GAMMA_H2S / 3 * tempSyn3) / tempSyn;
        cpSO2InSyngas = (ALPHA_SO2 * tempSyn + (BETA_SO2) / 2 * tempSyn2 + GAMMA_SO2 / 3 * tempSyn3) / tempSyn;
        cpNH3InSyngas = (ALPHA_NH3 * tempSyn + (BETA_NH3) / 2 * tempSyn2 + GAMMA_NH3 / 3 * tempSyn3) / tempSyn;
        cpNOInSyngas = (ALPHA_NO * tempSyn + (BETA_NO) / 2 * tempSyn2 + GAMMA_NO / 3 * tempSyn3) / tempSyn;

        int preAirIndex = 0;
        double totalHeat;
        sumNCpGas = 0;
        for (int speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * massFlowValue);

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length)
                    && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        molFlowArSyngas = massValue / AR_MW;
                        sumNCpGas += cpArInSyngas * molFlowArSyngas;
                        break;

                    case 1:
                        molFlowC6H6Syngas = massValue / (C_MW * 6.0 + H_MW * 6);
                        sumNCpGas += cpC6H6InSyngas * molFlowC6H6Syngas;
                        break;

                    case 2:
                        molFlowCH4Syngas = massValue / (C_MW + H_MW * 4.0);
                        sumNCpGas += cpCH4InSyngas * molFlowCH4Syngas;
                        break;

                    case 3:
                        molFlowCOSyngas = massValue / (C_MW + O_MW);
                        sumNCpGas += cpCOInSyngas * molFlowCOSyngas;
                        break;

                    case 4:
                        molFlowCO2Syngas = massValue / (C_MW + O_MW * 2.0);
                        sumNCpGas += cpCO2InSyngas * molFlowCO2Syngas;
                        break;

                    case 5:
                        molFlowH2Syngas = massValue / (H_MW * 2.0);
                        sumNCpGas += cpH2InSyngas * molFlowH2Syngas;
                        break;

                    case 6:
                        molFlowH2OSyngas = massValue / (H_MW * 2 + O_MW);
                        sumNCpGas += cpH2OInSyngas * molFlowH2OSyngas;
                        break;

                    case 7:
                        molFlowH2SSyngas = massValue / (H_MW * 2 + S_MW);
                        sumNCpGas += cpH2SInSyngas * molFlowH2SSyngas;
                        break;

                    case 8:
                        molFlowHClSyngas = massValue / (H_MW + CL_MW);
                        sumNCpGas += cpHClInSyngas * molFlowHClSyngas;
                        break;

                    case 9:
                        molFlowN2Syngas = massValue / (N_MW * 2);
                        sumNCpGas += cpN2InSyngas * molFlowN2Syngas;
                        break;

                    case 10:
                        molFlowNH3Syngas = massValue / (N_MW + H_MW * 3);
                        sumNCpGas += cpNH3InSyngas * molFlowNH3Syngas;
                        break;

                    case 11:
                        molFlowNOSyngas = massValue / (N_MW + O_MW);
                        sumNCpGas += cpNOInSyngas * molFlowNOSyngas;
                        break;

                    case 13:
                        molFlowO2Syngas = massValue / (O_MW * 2);
                        sumNCpGas += cpO2InSyngas * molFlowO2Syngas;
                        break;

                    case 15:
                        molFlowSO2Syngas = massValue / (S_MW + 2 * O_MW);
                        sumNCpGas += cpSO2InSyngas * molFlowSO2Syngas;
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        double U = Double.parseDouble((String) textFieldHash.get("tempLossPerFoot"));
        double currentTempF;
        
        double ambientTemp = Double.parseDouble((String) textFieldHash.get("ambientTemp"));

        outerArea = Math.PI * (toDiameter + 2) * deltaIncrement;// A = pi*D*L (L is equal to increment length)
        currentTempF = newTempK * 9 / 5.0 - 459.67;
        totalHeat = U * outerArea * (currentTempF - ambientTemp); //Btu/hr
        currentTempF = currentTempF - (totalHeat / sumNCpGas);

        tempGuessK = (currentTempF + 459.67) * 5 / 9.;
        readTemp = tempGuessK + ""; // degrees Kelvin

    }

    private void doEnergyBalance1() {

        doMassBalance1();

        Double[] massFractionsB4;
        massFractionsB4 = new Double[numSpecies];
        
        double startTime = finalTime;

        for (int y = 0; y < numSpecies; y++) {
            massFractionsB4[y] = Double.parseDouble(massFractions[y]);
        }

        deltaHCombustionRing1 = 0.0;
        int preAirIndex = 0, speciesIndex;
        double arraySum = 0.0, lbsO2needed, lbsO2Remaining = 0.0,
                massValue, combustibleC6H6 = 0.0, combustibleCH4 = 0.0,
                combustibleH2S = 0.0, combustibleNH3 = 0.0, combustibleCO = 0.0, combustibleH2 = 0.0,
                totalO2 = 0.0;

        double[] massValuesArray = new double[numSpecies];

        String[] speciesAfter = {"H2", "H2O", "O2"};

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 2: // O2
                        totalO2 = massValue / (O_MW * 2);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        preAirIndex = 0;
        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {
                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 1:
                        lbsO2needed = massValue * (7.5 / (6 * C_MW + 6 * H_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Benzene is completely oxidized
                            combustibleC6H6 = massValue; // lbs C6H6/hr
                            deltaHCombustionRing1 += combustibleC6H6 * 17450.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleC6H6 = totalO2 * (6 * C_MW + 6 * H_MW) / 7.5;
                            deltaHCombustionRing1 += combustibleC6H6 * 17450.0;
                            massValue -= combustibleC6H6;
                            totalO2 = 0.0;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 2: // CH4 + 1.5O2 --> CO + 2H2O Hrxn = 13985 Btu/lb
                        lbsO2needed = massValue * (1.5 / (C_MW + 4 * H_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume methane is completely oxidized
                            combustibleCH4 = massValue;
                            deltaHCombustionRing1 += combustibleCH4 * 13985.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleCH4 = lbsO2Remaining * (C_MW + 4 * H_MW) / (1.5);
                            deltaHCombustionRing1 += combustibleCH4 * 13985.0;
                            massValue -= combustibleCH4;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        massValue += (combustibleCH4 / (C_MW + 4 * H_MW)) * (C_MW + O_MW);
                        break;

                    case 4: // CO2
                        massValue += (6 * combustibleC6H6 / (6 * C_MW + H_MW * 6)) * (C_MW + 2 * O_MW);
                        break;

                    case 7: // H2S + 1.5O2 --> H2O + SO2 Hrxn = 6000 cal/lb
                        lbsO2needed = massValue * (1.5 / (H_MW * 2 + S_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleH2S = massValue;
                            deltaHCombustionRing1 += combustibleH2S * 6000; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleH2S = lbsO2Remaining * (S_MW + 2 * H_MW) / (1.5);
                            deltaHCombustionRing1 += combustibleH2S * 6000;
                            massValue -= combustibleH2S;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 10: // NH3 + 1.25O2 --> NO + 1.5H2O Hrxn = 6410
                        lbsO2needed = massValue * (1.25 / (H_MW * 3 + N_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume ammonia is completely oxidized
                            combustibleNH3 = massValue;
                            deltaHCombustionRing1 += combustibleNH3 * 6410.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleNH3 = lbsO2Remaining * (N_MW + 3 * H_MW) / (1.25);
                            deltaHCombustionRing1 += combustibleNH3 * 6410.0;
                            massValue -= combustibleNH3;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 11: // NO
                        massValue += (combustibleNH3 / (N_MW + 3 * H_MW)) * (N_MW + O_MW);
                        break;

                    case 15: // SO2
                        massValue += (combustibleH2S / (H_MW * 2 + S_MW)) * (S_MW + O_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;

        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        lbsO2needed = massValue * (0.5 / (C_MW + O_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Carbon Monoxide is completely oxidized
                            combustibleCO = massValue; // lbs C6H6/hr
                            deltaHCombustionRing1 += combustibleCO * 4346.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleCO = totalO2 * (6 * C_MW + 6 * H_MW) / 7.5;
                            deltaHCombustionRing1 += combustibleCO * 17450.0;
                            massValue -= combustibleCO;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 4: // CO2
                        massValue += (combustibleCO / (C_MW + O_MW)) * (C_MW + 2 * O_MW);
                        break;

                    case 5: //H2 + 0.5 O2 --> H2O Hrxn = 51972 Btu/lb
                        lbsO2needed = massValue * (0.5 / (2 * H_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Hydrogen is completely oxidized
                            combustibleH2 = massValue; // lbs H2/hr
                            deltaHCombustionRing1 += combustibleH2 * 51972.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleH2 = totalO2 * (2 * H_MW) / 0.5;
                            deltaHCombustionRing1 += combustibleH2 * 51972.0;
                            massValue -= combustibleH2;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 13: // O2
                        massValue = lbsO2Remaining * (O_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        arraySum = 0.0;
        preAirIndex = 0;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 1:
                        massValue += (2 * combustibleCH4 / (C_MW + 4 * H_MW) + 3 * combustibleC6H6 / (C_MW * 6 + 6 * H_MW)
                                + 1.5 * combustibleNH3 / (N_MW + 3 * H_MW) + combustibleH2S / (H_MW * 2 + S_MW)
                                + combustibleH2 / (2 * H_MW)) * (H_MW * 2 + O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }
            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        //Convert back to mass fraction and store in mass fraction array
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;
        double delta;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        molFlowArSyngas = massValue / AR_MW;
                        break;

                    case 1:
                        molFlowC6H6Syngas = massValue / (C_MW * 6.0 + H_MW * 6);
                        break;

                    case 2:
                        molFlowCH4Syngas = massValue / (C_MW + H_MW * 4.0);
                        break;

                    case 3:
                        molFlowCOSyngas = massValue / (C_MW + O_MW);
                        break;

                    case 4:
                        molFlowCO2Syngas = massValue / (C_MW + O_MW * 2.0);
                        break;

                    case 5:
                        molFlowH2Syngas = massValue / (H_MW * 2.0);
                        break;

                    case 6:
                        molFlowH2OSyngas = massValue / (H_MW * 2 + O_MW);
                        break;

                    case 7:
                        molFlowH2SSyngas = massValue / (H_MW * 2 + S_MW);
                        break;

                    case 8:
                        molFlowHClSyngas = massValue / (H_MW + CL_MW);
                        break;

                    case 9:
                        molFlowN2Syngas = massValue / (N_MW * 2);
                        break;

                    case 10:
                        molFlowNH3Syngas = massValue / (N_MW + H_MW * 3);
                        break;

                    case 11:
                        molFlowNOSyngas = massValue / (N_MW + O_MW);
                        break;

                    case 13:
                        molFlowO2Syngas = massValue / (O_MW * 2);
                        break;

                    case 15:
                        molFlowSO2Syngas = massValue / (S_MW + 2 * O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        sumDeltaHRing1 = sumDeltaHRing1B4Combustion + deltaHCombustionRing1;

        tempGuessK = 1000;
        // Temperature iteration before reactions
        while (true) {

            cpN2Guess = (ALPHA_N2 * (tempGuessK - 298) + (BETA_N2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + (GAMMA_N2) / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpO2Guess = (ALPHA_O2 * (tempGuessK - 298) + (BETA_O2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_O2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpArGuess = (ALPHA_AR * (tempGuessK - 298) + (BETA_AR) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_AR / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2OGuess = (ALPHA_H2O * (tempGuessK - 298) + (BETA_H2O) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2O / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCO2Guess = (ALPHA_CO2 * (tempGuessK - 298) + (BETA_CO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCOGuess = (ALPHA_CO * (tempGuessK - 298) + (BETA_CO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCH4Guess = (ALPHA_CH4 * (tempGuessK - 298) + (BETA_CH4) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CH4 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpC6H6Guess = (ALPHA_C6H6 * (tempGuessK - 298) + (BETA_C6H6) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_C6H6 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2Guess = (ALPHA_H2 * (tempGuessK - 298) + (BETA_H2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpHClGuess = (ALPHA_HCL * (tempGuessK - 298) + (BETA_HCL) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_HCL / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2SlGuess = (ALPHA_H2S * (tempGuessK - 298) + (BETA_H2S) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2S / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpSO2lGuess = (ALPHA_SO2 * (tempGuessK - 298) + (BETA_SO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_SO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNH3lGuess = (ALPHA_NH3 * (tempGuessK - 298) + (BETA_NH3) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NH3 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNOGuess = (ALPHA_NO * (tempGuessK - 298) + (BETA_NO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);

            tempGuessF = tempGuessK * 9 / 5. - 459.67;

            guessHeat = (cpN2Guess * molFlowN2Syngas + cpO2Guess * molFlowO2Syngas
                    + cpArGuess * molFlowArSyngas + cpH2OGuess * molFlowH2OSyngas + cpCO2Guess * molFlowCO2Syngas + cpCOGuess * molFlowCOSyngas
                    + cpCH4Guess * molFlowCH4Syngas + cpC6H6Guess * molFlowC6H6Syngas + cpH2Guess * molFlowH2Syngas + cpH2SlGuess * molFlowH2SSyngas
                    + cpNH3lGuess * molFlowNH3Syngas + cpHClGuess * molFlowHClSyngas + cpNOGuess * molFlowNOSyngas + cpSO2lGuess * molFlowSO2Syngas) * (tempGuessF - 77);

            sumNCp = guessHeat / (tempGuessF - 77);

            tempGuessF = 77 + sumDeltaHRing1 / sumNCp;

            delta = sumDeltaHRing1 - guessHeat;

            tempGuessK = (tempGuessF + 459.67) * 5 / 9.;

            //If delta==0, then exit loop
            if (delta < EPSILON) {
                break;
            }

        }

        for (int y = 0; y < numSpecies; y++) {
            massFractions[y] = massFractionsB4[y] + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;
        convertMassFractionToMol(true);
        readTemp = tempGuessK + "";

        double timeB4Final = finalTime;
        calculateVelocityMock();
        
        double timeAfter = finalTime;

        Scanner scanFile;
        Formatter formatFile;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\temp-SOLTMP.txt"));

            formatFile = new Formatter(new File("CHEMKED\\SOLTMP.txt"));

            int i = 0;

            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();

                if (i == 14) {
                    fileLine = " " + 0 + " !Temperature Equation";
                }

                formatFile.format("%s%n", fileLine);

                if (fileLine.matches(" THERMO")) {
                    break;
                }

                i++;
            }

            //Print the remaining lines
            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();
                formatFile.format("%s%n", fileLine);
            }

            scanFile.close();
            formatFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


        updateFile();
        rewriteSOLTMP(true);

        try {

            FileOutputStream fos = new FileOutputStream("CHEMKED\\error_trial.txt");

            String[] CHEMKED = {"CHEMKED\\CHEMKED_SOLVER.exe", "CHEMKED\\SOLTMP.txt"};
            ProcessBuilder builder = new ProcessBuilder(CHEMKED);
            builder.directory(new File("CHEMKED\\"));
            builder.redirectError();
            Process pr = builder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            // any error message?
            ThermalOxidizer.StreamGobbler errorGobbler = new ThermalOxidizer.StreamGobbler(pr.getErrorStream(), "ERROR");

            // any output?
            ThermalOxidizer.StreamGobbler outputGobbler = new ThermalOxidizer.StreamGobbler(pr.getInputStream(), "OUTPUT", fos);

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            int exitVal;
            exitVal = pr.waitFor();
            System.out.println("Exited with error code " + exitVal);
            fos.flush();
            fos.close();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.toString());
            e.printStackTrace(System.out);
        }

        int currentRunNumAfter = 80;
        shortenSOLTMP(currentRunNumAfter);
        copyCurrentRunSOLTMP(currentRunNumAfter);
        getNumOutputLines(currentRunNumAfter);
        getMassFractions(currentRunNumAfter);
        convertMassFractionToMol(false);

        Double[] massFractionsAfter;
        massFractionsAfter = new Double[numSpecies];

        for (int t = 0; t < numSpecies; t++) {
            massFractionsAfter[t] = Double.parseDouble(massFractions[t]);
        }

        for (int y = 0; y < numSpecies; y++) {
            massFractions[y] = massFractionsB4[y] + "";
        }

        finalTime = timeB4Final;
        deltaHCombustionRing1 = 0.0;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 2: // O2
                        totalO2 = massValue / (O_MW * 2);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        preAirIndex = 0;
        double destructionRemovalEfficiency;
        
        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 1: // C6H6 + 7.5O2 --> 6CO2 + 3H2O Hrxn = 17450 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (7.5 / (6 * C_MW + 6 * H_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Benzene is completely oxidized
                            combustibleC6H6 = massValue * destructionRemovalEfficiency; // lbs C6H6/hr
                            deltaHCombustionRing1 += combustibleC6H6 * 17450.0; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleC6H6 = totalO2 * (totalMassFlowRateAfterRing1) * (6 * C_MW + 6 * H_MW) / 7.5;
                            deltaHCombustionRing1 += combustibleC6H6 * 17450.0;
                            massValue -= combustibleC6H6;
                            totalO2 = 0.0;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 2: // CH4 + 1.5O2 --> CO + 2H2O Hrxn = 13985 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (1.5 / (C_MW + 4 * H_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume methane is completely oxidized
                            combustibleCH4 = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing1 += combustibleCH4 * 13985.0; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleCH4 = lbsO2Remaining * (C_MW + 4 * H_MW) / (1.5);
                            deltaHCombustionRing1 += combustibleCH4 * 13985.0;
                            massValue -= combustibleCH4;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        massValue += (combustibleCH4 / (C_MW + 4 * H_MW)) * (C_MW + O_MW);
                        break;

                    case 4: // CO2
                        massValue += (6 * combustibleC6H6 / (6 * C_MW + H_MW * 6)) * (C_MW + 2 * O_MW);
                        break;

                    case 10: // NH3 + 1.25O2 --> NO + 1.5H2O Hrxn = 6410
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (1.25 / (H_MW * 3 + N_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume ammonia is completely oxidized
                            combustibleNH3 = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing1 += combustibleNH3 * 6410.0; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleNH3 = lbsO2Remaining * (N_MW + 3 * H_MW) / (1.25);
                            deltaHCombustionRing1 += combustibleNH3 * 6410.0;
                            massValue -= combustibleNH3;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 11: // NO
                        massValue += (combustibleNH3 / (N_MW + 3 * H_MW)) * (N_MW + O_MW);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;
        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 7: // H2S + 1.5O2 --> H2O + SO2 Hrxn = 6000 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (1.5 / (H_MW * 2 + S_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleH2S = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing1 += combustibleH2S * 6000; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleH2S = lbsO2Remaining * (S_MW + 2 * H_MW) / (1.5);
                            deltaHCombustionRing1 += combustibleH2S * 6000;
                            massValue -= combustibleH2S;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 15: // SO2
                        massValue += (combustibleH2S / (H_MW * 2 + S_MW)) * (S_MW + O_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        preAirIndex = 0;
        arraySum = 0.0;

        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 5: // H2 + 0.5O2 --> H2O Hrxn = 51972 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (0.5 / (H_MW * 2)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleH2 = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing1 += combustibleH2 * 51972; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleH2 = lbsO2Remaining * (2 * H_MW) / (0.5);
                            deltaHCombustionRing1 += combustibleH2 * 51972;
                            massValue -= combustibleH2;
                            lbsO2Remaining = 0.0;
                        }

                        break;
                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }
        arraySum = 0.0;
        preAirIndex = 0;

        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (0.5 / (C_MW + O_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleCO = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing1 += combustibleCO * 4346; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleCO = lbsO2Remaining * (C_MW + O_MW) / (0.5);
                            deltaHCombustionRing1 += combustibleCO * 51972;
                            massValue -= combustibleH2;
                            lbsO2Remaining = 0.0;
                        }

                        break;

                    case 4: // CO2
                        massValue += combustibleCO * (C_MW + O_MW * 2) / (C_MW + O_MW);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        arraySum = 0.0;
        preAirIndex = 0;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 1:
                        massValue += (2 * combustibleCH4 / (C_MW + 4 * H_MW) + 3 * combustibleC6H6 / (C_MW * 6 + 6 * H_MW)
                                + 1.5 * combustibleNH3 / (N_MW + 3 * H_MW) + combustibleH2S / (H_MW * 2 + S_MW)
                                + combustibleH2 / (2 * H_MW)) * (H_MW * 2 + O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }
            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        //Convert back to mass fraction and store in mass fraction array
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 2: // O2
                        massValue = lbsO2Remaining * (O_MW * 2);
                        molFlowO2Syngas = massValue;
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }
        
        preAirIndex = 0;

        sumDeltaHRing1 = sumDeltaHRing1B4Combustion + deltaHCombustionRing1;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing1));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        molFlowArSyngas = massValue / AR_MW;
                        break;

                    case 1:
                        molFlowC6H6Syngas = massValue / (C_MW * 6.0 + H_MW * 6);
                        break;

                    case 2:
                        molFlowCH4Syngas = massValue / (C_MW + H_MW * 4.0);
                        break;

                    case 3:
                        molFlowCOSyngas = massValue / (C_MW + O_MW);
                        break;

                    case 4:
                        molFlowCO2Syngas = massValue / (C_MW + O_MW * 2.0);
                        break;

                    case 5:
                        molFlowH2Syngas = massValue / (H_MW * 2.0);
                        break;

                    case 6:
                        molFlowH2OSyngas = massValue / (H_MW * 2 + O_MW);
                        break;

                    case 7:
                        molFlowH2SSyngas = massValue / (H_MW * 2 + S_MW);
                        break;

                    case 8:
                        molFlowHClSyngas = massValue / (H_MW + CL_MW);
                        break;

                    case 9:
                        molFlowN2Syngas = massValue / (N_MW * 2);
                        break;

                    case 10:
                        molFlowNH3Syngas = massValue / (N_MW + H_MW * 3);
                        break;

                    case 11:
                        molFlowNOSyngas = massValue / (N_MW + O_MW);
                        break;

                    case 13:
                        molFlowO2Syngas = massValue / (O_MW * 2);
                        break;

                    case 15:
                        molFlowSO2Syngas = massValue / (S_MW + 2 * O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }  

        tempGuessK = 1000.0;
        // Temperature iteration before reactions
        while (true) {

            cpN2Guess = (ALPHA_N2 * (tempGuessK - 298) + (BETA_N2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + (GAMMA_N2) / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpO2Guess = (ALPHA_O2 * (tempGuessK - 298) + (BETA_O2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_O2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpArGuess = (ALPHA_AR * (tempGuessK - 298) + (BETA_AR) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_AR / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2OGuess = (ALPHA_H2O * (tempGuessK - 298) + (BETA_H2O) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2O / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCO2Guess = (ALPHA_CO2 * (tempGuessK - 298) + (BETA_CO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCOGuess = (ALPHA_CO * (tempGuessK - 298) + (BETA_CO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCH4Guess = (ALPHA_CH4 * (tempGuessK - 298) + (BETA_CH4) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CH4 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpC6H6Guess = (ALPHA_C6H6 * (tempGuessK - 298) + (BETA_C6H6) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_C6H6 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2Guess = (ALPHA_H2 * (tempGuessK - 298) + (BETA_H2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpHClGuess = (ALPHA_HCL * (tempGuessK - 298) + (BETA_HCL) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_HCL / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2SlGuess = (ALPHA_H2S * (tempGuessK - 298) + (BETA_H2S) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2S / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpSO2lGuess = (ALPHA_SO2 * (tempGuessK - 298) + (BETA_SO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_SO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNH3lGuess = (ALPHA_NH3 * (tempGuessK - 298) + (BETA_NH3) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NH3 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNOGuess = (ALPHA_NO * (tempGuessK - 298) + (BETA_NO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);

            tempGuessF = tempGuessK * 9 / 5. - 459.67;

            guessHeat = (cpN2Guess * molFlowN2Syngas + cpO2Guess * molFlowO2Syngas
                    + cpArGuess * molFlowArSyngas + cpH2OGuess * molFlowH2OSyngas + cpCO2Guess * molFlowCO2Syngas + cpCOGuess * molFlowCOSyngas
                    + cpCH4Guess * molFlowCH4Syngas + cpC6H6Guess * molFlowC6H6Syngas + cpH2Guess * molFlowH2Syngas + cpH2SlGuess * molFlowH2SSyngas
                    + cpNH3lGuess * molFlowNH3Syngas + cpHClGuess * molFlowHClSyngas + cpNOGuess * molFlowNOSyngas + cpSO2lGuess * molFlowSO2Syngas) * (tempGuessF - 77);

            sumNCp = guessHeat / (tempGuessF - 77);

            tempGuessF = 77 + sumDeltaHRing1 / sumNCp;

            delta = sumDeltaHRing1 - guessHeat;

            tempGuessK = 5 / 9. * (tempGuessF + 459.67); // Degrees K

            //If delta==0, then exit loop
            if (delta < EPSILON) {
                break;
            }

        }

        for (int y = 0; y < numSpecies; y++) {
            massFractions[y] = massFractionsB4[y] + "";
        }
        convertMassFractionToMol(true);

        readTemp = tempGuessK + "";   //is this necessary??!!
        
        finalTime = timeB4Final;
        
        double heatLossCoefficient = Double.parseDouble((String) textFieldHash.get("tempLossPerFoot"));
        double ambientTemp = Double.parseDouble((String) textFieldHash.get("ambientTemp"));
        
        double readTempVal = Double.parseDouble(readTemp);
        
        if(deltaHCombustionRing1>heatLossCoefficient*outerArea*(readTempVal-ambientTemp)){
        
            readTempVal = readTempVal - (timeAfter - startTime)*(deltaHCombustionRing1 - heatLossCoefficient*outerArea*(readTempVal - ambientTemp))/sumNCp;

            
        }
        else{
            readTempVal = readTempVal - heatLossCoefficient*outerArea*(readTempVal-ambientTemp);
        }
        
        readTemp = readTempVal + "";            
                    
        calculateVelocity();
    }

    private void doEnergyBalance2() {
        
        double startTime = finalTime;

        int preAirIndex3 = 0, preAirIndex = 0;
        double arraySum = 0.0, totalO2 = 0.0, massValue;
        Double[] massValuesArray;
        massValuesArray = new Double[numSpecies];

        doMassBalance2();

        Double[] massFractionsB4;
        massFractionsB4 = new Double[numSpecies];

        for (int y = 0; y < numSpecies; y++) {
            massFractionsB4[y] = Double.parseDouble(massFractions[y]);
        }

        String[] speciesAfter = {"H2", "H2O", "O2"};

        for (int speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 2: // O2
                        totalO2 = massValue / (O_MW * 2);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }
        arraySum = 0.0;
        int speciesIndex;

        double combustibleCO = 0.0, combustibleH2 = 0.0, lbsO2needed, lbsO2Remaining = 0.0, combustibleC6H6 = 0.0, combustibleCH4 = 0.0, combustibleNH3 = 0.0,
                combustibleH2S = 0.0;

        preAirIndex = 0;
        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 1: // C6H6 + 7.5O2 --> 6CO2 + 3H2O Hrxn = 17450 Btu/lb
                        lbsO2needed = massValue * (7.5 / (6 * C_MW + 6 * H_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Benzene is completely oxidized
                            combustibleC6H6 = massValue; // lbs C6H6/hr
                            deltaHCombustionRing2 += combustibleC6H6 * 17450.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleC6H6 = lbsO2Remaining * (6 * C_MW + 6 * H_MW) / 7.5;
                            deltaHCombustionRing2 += combustibleC6H6 * 17450.0;
                            massValue -= combustibleC6H6;
                            totalO2 = 0.0;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 2: // CH4 + 1.5O2 --> CO + 2H2O Hrxn = 13985 Btu/lb
                        lbsO2needed = massValue * (1.5 / (C_MW + 4 * H_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume methane is completely oxidized
                            combustibleCH4 = massValue;
                            deltaHCombustionRing2 += combustibleCH4 * 13985.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleCH4 = lbsO2Remaining * (C_MW + 4 * H_MW) / (1.5);
                            deltaHCombustionRing2 += combustibleCH4 * 13985.0;
                            massValue -= combustibleCH4;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        massValue += (combustibleCH4 / (C_MW + 4 * H_MW)) * (C_MW + O_MW);
                        break;

                    case 4: // CO2
                        massValue += (6 * combustibleC6H6 / (6 * C_MW + H_MW * 6)) * (C_MW + 2 * O_MW);
                        break;

                    case 7: // H2S + 1.5O2 --> H2O + SO2 Hrxn = 6000 cal/lb
                        lbsO2needed = massValue * (1.5 / (H_MW * 2 + S_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleH2S = massValue;
                            deltaHCombustionRing2 += combustibleH2S * 6000; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleH2S = lbsO2Remaining * (S_MW + 2 * H_MW) / (1.5);
                            deltaHCombustionRing2 += combustibleH2S * 6000;
                            massValue -= combustibleH2S;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 10: // NH3 + 1.25O2 --> NO + 1.5H2O Hrxn = 6410
                        lbsO2needed = massValue * (1.25 / (H_MW * 3 + N_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume ammonia is completely oxidized
                            combustibleNH3 = massValue;
                            deltaHCombustionRing2 += combustibleNH3 * 6410.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleNH3 = lbsO2Remaining * (N_MW + 3 * H_MW) / (1.25);
                            deltaHCombustionRing2 += combustibleNH3 * 6410.0;
                            massValue -= combustibleNH3;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 11: // NO
                        massValue += (combustibleNH3 / (N_MW + 3 * H_MW)) * (N_MW + O_MW);
                        break;

                    case 15: // SO2
                        massValue += (combustibleH2S / (H_MW * 2 + S_MW)) * (S_MW + O_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;

        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex3])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        lbsO2needed = massValue * (0.5 / (C_MW + O_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Carbon Monoxide is completely oxidized
                            combustibleCO = massValue; // lbs C6H6/hr
                            deltaHCombustionRing2 += combustibleCO * 4346.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleCO = lbsO2Remaining * (C_MW + O_MW) / 7.5;
                            deltaHCombustionRing2 += combustibleCO * 17450.0;
                            massValue -= combustibleCO;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 4: // CO2
                        massValue += (combustibleCO / (C_MW + O_MW)) * (C_MW + 2 * O_MW);
                        break;

                    case 5: //H2 + 0.5 O2 --> H2O Hrxn = 51972 Btu/lb	
                        lbsO2needed = massValue * (0.5 / (2 * H_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Hydrogen is completely oxidized
                            combustibleH2 = massValue; // lbs H2/hr
                            deltaHCombustionRing2 += combustibleH2 * 51972.0; // Btu/hr
                            massValue = 0.0;
                        } else {
                            combustibleH2 = lbsO2Remaining * (2 * H_MW) / 0.5;
                            deltaHCombustionRing2 += combustibleH2 * 51972.0;
                            massValue -= combustibleH2;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 6: //H2O
                        massValue += (combustibleH2 / (2 * H_MW)) * (H_MW * 2 + O_MW);
                        break;

                    case 13: // O2
                        massValue = lbsO2Remaining * (O_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        molFlowArSyngas = massValue / AR_MW;
                        break;

                    case 1:
                        molFlowC6H6Syngas = massValue / (C_MW * 6.0 + H_MW * 6);
                        break;

                    case 2:
                        molFlowCH4Syngas = massValue / (C_MW + H_MW * 4.0);
                        break;

                    case 3:
                        molFlowCOSyngas = massValue / (C_MW + O_MW);
                        break;

                    case 4:
                        molFlowCO2Syngas = massValue / (C_MW + O_MW * 2.0);
                        break;

                    case 5:
                        molFlowH2Syngas = massValue / (H_MW * 2.0);
                        break;

                    case 6:
                        molFlowH2OSyngas = massValue / (H_MW * 2 + O_MW);
                        break;

                    case 7:
                        molFlowH2SSyngas = massValue / (H_MW * 2 + S_MW);
                        break;

                    case 8:
                        molFlowHClSyngas = massValue / (H_MW + CL_MW);
                        break;

                    case 9:
                        molFlowN2Syngas = massValue / (N_MW * 2);
                        break;

                    case 10:
                        molFlowNH3Syngas = massValue / (N_MW + H_MW * 3);
                        break;

                    case 11:
                        molFlowNOSyngas = massValue / (N_MW + O_MW);
                        break;

                    case 13:
                        molFlowO2Syngas = massValue / (O_MW * 2);
                        break;

                    case 15:
                        molFlowSO2Syngas = massValue / (S_MW + 2 * O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        sumDeltaHRing2 = sumDeltaHRing2B4Combustion + deltaHCombustionRing1 + deltaHCombustionRing2;
        
        double delta;
        tempGuessK = 1500.0;
        // Temperature iteration before reactions
        while (true) {

            cpN2Guess = (ALPHA_N2 * (tempGuessK - 298) + (BETA_N2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + (GAMMA_N2) / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpO2Guess = (ALPHA_O2 * (tempGuessK - 298) + (BETA_O2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_O2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpArGuess = (ALPHA_AR * (tempGuessK - 298) + (BETA_AR) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_AR / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2OGuess = (ALPHA_H2O * (tempGuessK - 298) + (BETA_H2O) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2O / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCO2Guess = (ALPHA_CO2 * (tempGuessK - 298) + (BETA_CO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCOGuess = (ALPHA_CO * (tempGuessK - 298) + (BETA_CO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCH4Guess = (ALPHA_CH4 * (tempGuessK - 298) + (BETA_CH4) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CH4 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpC6H6Guess = (ALPHA_C6H6 * (tempGuessK - 298) + (BETA_C6H6) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_C6H6 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2Guess = (ALPHA_H2 * (tempGuessK - 298) + (BETA_H2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpHClGuess = (ALPHA_HCL * (tempGuessK - 298) + (BETA_HCL) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_HCL / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2SlGuess = (ALPHA_H2S * (tempGuessK - 298) + (BETA_H2S) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2S / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpSO2lGuess = (ALPHA_SO2 * (tempGuessK - 298) + (BETA_SO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_SO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNH3lGuess = (ALPHA_NH3 * (tempGuessK - 298) + (BETA_NH3) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NH3 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNOGuess = (ALPHA_NO * (tempGuessK - 298) + (BETA_NO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);

            tempGuessF = tempGuessK * 9 / 5. - 459.67;

            guessHeat = (cpN2Guess * molFlowN2Syngas + cpO2Guess * molFlowO2Syngas
                    + cpArGuess * molFlowArSyngas + cpH2OGuess * molFlowH2OSyngas + cpCO2Guess * molFlowCO2Syngas + cpCOGuess * molFlowCOSyngas
                    + cpCH4Guess * molFlowCH4Syngas + cpC6H6Guess * molFlowC6H6Syngas + cpH2Guess * molFlowH2Syngas + cpH2SlGuess * molFlowH2SSyngas
                    + cpNH3lGuess * molFlowNH3Syngas + cpHClGuess * molFlowHClSyngas + cpNOGuess * molFlowNOSyngas + cpSO2lGuess * molFlowSO2Syngas) * (tempGuessF - 77);

            sumNCp = guessHeat / (tempGuessF - 77);

            tempGuessF = 77 + sumDeltaHRing2 / sumNCp;

            delta = sumDeltaHRing2 - guessHeat;

            tempGuessK = 5 / 9. * (tempGuessK + 459.67); // Kelvin

            //If delta==0, then exit loop
            if (delta < EPSILON) {
                break;
            } // end of if

        } // end of while loop

        for (int y = 0; y < numSpecies; y++) {
            massFractions[y] = massFractionsB4[y] + "";
        }

        convertMassFractionToMol(true);
        readTemp = tempGuessK + "";

        double timeB4Final = finalTime;
        calculateVelocityMock();
        
        double timeAfter = finalTime;

        Scanner scanFile;
        Formatter formatFile;

        try {

            scanFile = new Scanner(new FileReader("CHEMKED\\temp-SOLTMP.txt"));

            formatFile = new Formatter(new File("CHEMKED\\SOLTMP.txt"));

            int i = 0;

            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();

                if (i == 14) {
                    fileLine = " " + 0 + " !Temperature Equation";
                }

                formatFile.format("%s%n", fileLine);

                if (fileLine.matches(" THERMO")) {
                    break;
                }

                i++;
            }

            //Print the remaining lines
            while (scanFile.hasNextLine()) {
                fileLine = scanFile.nextLine();
                formatFile.format("%s%n", fileLine);
            }

            scanFile.close();
            formatFile.close();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


        updateFile();
        rewriteSOLTMP(true);

        try {

            FileOutputStream fos = new FileOutputStream("CHEMKED\\error_trial2.txt");

            String[] CHEMKED = {"CHEMKED\\CHEMKED_SOLVER.exe", "CHEMKED\\SOLTMP.txt"};
            ProcessBuilder builder = new ProcessBuilder(CHEMKED);
            builder.directory(new File("CHEMKED\\"));
            builder.redirectError();
            Process pr = builder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            // any error message?
            ThermalOxidizer.StreamGobbler errorGobbler = new ThermalOxidizer.StreamGobbler(pr.getErrorStream(), "ERROR");

            // any output?
            ThermalOxidizer.StreamGobbler outputGobbler = new ThermalOxidizer.StreamGobbler(pr.getInputStream(), "OUTPUT", fos);

            // kick them off
            errorGobbler.start();
            outputGobbler.start();

            int exitVal;
            exitVal = pr.waitFor();
            System.out.println("Exited with error code " + exitVal);
            fos.flush();
            fos.close();

        } catch (IOException | InterruptedException e) {
            System.out.println(e.toString());
            e.printStackTrace(System.out);
        }

        int currentRunNumAfter = 90;
        shortenSOLTMP(currentRunNumAfter);
        copyCurrentRunSOLTMP(currentRunNumAfter);
        getNumOutputLines(currentRunNumAfter);
        getMassFractions(currentRunNumAfter);
        convertMassFractionToMol(false);

        Double[] massFractionsAfter;
        massFractionsAfter = new Double[numSpecies];

        for (int t = 0; t < numSpecies; t++) {
            massFractionsAfter[t] = Double.parseDouble(massFractions[t]);
        }

        for (int y = 0; y < numSpecies; y++) {
            massFractions[y] = massFractionsB4[y] + "";
        }

        finalTime = timeB4Final;

        preAirIndex = 0;
        deltaHCombustionRing2 = 0.0;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesAfter.length) && speciesNames[speciesIndex].matches(speciesAfter[preAirIndex])) {

                //index values for species of interest
                //speciesAfter = {"H2", "H2O", "O2"};
                switch (preAirIndex) {

                    case 2: // O2
                        totalO2 = massValue / (O_MW * 2);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        double destructionRemovalEfficiency;
        preAirIndex = 0;
        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 1: // C6H6 + 7.5O2 --> 6CO2 + 3H2O Hrxn = 17450 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (7.5 / (6 * C_MW + 6 * H_MW)); //Stoichiometric amount of oxygen
                        if (totalO2 - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume Benzene is completely oxidized
                            combustibleC6H6 = massValue * destructionRemovalEfficiency; // lbs C6H6/hr
                            deltaHCombustionRing2 += combustibleC6H6 * 17450.0; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleC6H6 = totalO2 * (totalMassFlowRateAfterRing1) * (6 * C_MW + 6 * H_MW) / 7.5;
                            deltaHCombustionRing2 += combustibleC6H6 * 17450.0;
                            massValue -= combustibleC6H6;
                            totalO2 = 0.0;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 2: // CH4 + 1.5O2 --> CO + 2H2O Hrxn = 13985 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (1.5 / (C_MW + 4 * H_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume methane is completely oxidized
                            combustibleCH4 = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing2 += combustibleCH4 * 13985.0; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleCH4 = lbsO2Remaining * (C_MW + 4 * H_MW) / (1.5);
                            deltaHCombustionRing2 += combustibleCH4 * 13985.0;
                            massValue -= combustibleCH4;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        massValue += (combustibleCH4 / (C_MW + 4 * H_MW)) * (C_MW + O_MW);
                        break;

                    case 4: // CO2
                        massValue += (6 * combustibleC6H6 / (6 * C_MW + H_MW * 6)) * (C_MW + 2 * O_MW);
                        break;

                    case 10: // NH3 + 1.25O2 --> NO + 1.5H2O Hrxn = 6410
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (1.25 / (H_MW * 3 + N_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume ammonia is completely oxidized
                            combustibleNH3 = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing2 += combustibleNH3 * 6410.0; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleNH3 = lbsO2Remaining * (N_MW + 3 * H_MW) / (1.25);
                            deltaHCombustionRing2 += combustibleNH3 * 6410.0;
                            massValue -= combustibleNH3;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 11: // NO
                        massValue += (combustibleNH3 / (N_MW + 3 * H_MW)) * (N_MW + O_MW);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;
        arraySum = 0.0;
        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 7: // H2S + 1.5O2 --> H2O + SO2 Hrxn = 6000 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = massValue * destructionRemovalEfficiency * (1.5 / (H_MW * 2 + S_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleH2S = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing2 += combustibleH2S * 6000; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleH2S = lbsO2Remaining * (S_MW + 2 * H_MW) / (1.5);
                            deltaHCombustionRing2 += combustibleH2S * 6000;
                            massValue -= combustibleH2S;
                            lbsO2Remaining = 0.0;
                        }
                        break;

                    case 15: // SO2
                        massValue += (combustibleH2S / (H_MW * 2 + S_MW)) * (S_MW + O_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        preAirIndex = 0;
        arraySum = 0.0;


        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 5: // H2 + 0.5O2 --> H2O Hrxn = 51972 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = destructionRemovalEfficiency * massValue * (0.5 / (H_MW * 2)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining = totalO2 - lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleH2 = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing2 += combustibleH2 * 51972; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleH2 = lbsO2Remaining * (2 * H_MW) / (0.5);
                            deltaHCombustionRing2 += combustibleH2 * 51972;
                            massValue -= combustibleH2;
                            lbsO2Remaining = 0.0;
                        }

                        break;

                    case 6: // H2O
                        massValue += combustibleH2 * (H_MW * 2 + O_MW) / (H_MW * 2);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }
        arraySum = 0.0;
        preAirIndex = 0;

        // RXNs
        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (massFractionsB4[speciesIndex] * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 3: // CO + 0.5O2 --> CO2 Hrxn = 4346 Btu/lb
                        destructionRemovalEfficiency = 1 - massFractionsAfter[speciesIndex] / massFractionsB4[speciesIndex];
                        lbsO2needed = destructionRemovalEfficiency * massValue * (0.5 / (C_MW + O_MW)); //Stoichiometric amount of oxygen
                        if (lbsO2Remaining - lbsO2needed > EPSILON) {
                            lbsO2Remaining -= lbsO2needed;
                            // Assume hydrogen sulfide is completely oxidized
                            combustibleCO = massValue * destructionRemovalEfficiency;
                            deltaHCombustionRing2 += combustibleCO * 4346; // Btu/hr
                            massValue = massValue * (1 - destructionRemovalEfficiency);
                        } else {
                            combustibleCO = lbsO2Remaining * (C_MW + O_MW) / (0.5);
                            deltaHCombustionRing2 += combustibleCO * 51972;
                            massValue -= combustibleH2;
                            lbsO2Remaining = 0.0;
                        }

                        break;

                    case 4: // CO2
                        massValue += combustibleCO * (C_MW + O_MW * 2) / (C_MW + O_MW);
                        break;

                    default:
                        break;

                }
                preAirIndex++;
            }

            massValuesArray[speciesIndex] = massValue;
            arraySum += massValue;
        }

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {
            massFractions[speciesIndex] = (massValuesArray[speciesIndex] / arraySum) + "";
        }

        preAirIndex = 0;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterRing2));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        molFlowArSyngas = massValue / AR_MW;
                        break;

                    case 1:
                        molFlowC6H6Syngas = massValue / (C_MW * 6.0 + H_MW * 6);
                        break;

                    case 2:
                        molFlowCH4Syngas = massValue / (C_MW + H_MW * 4.0);
                        break;

                    case 3:
                        molFlowCOSyngas = massValue / (C_MW + O_MW);
                        break;

                    case 4:
                        molFlowCO2Syngas = massValue / (C_MW + O_MW * 2.0);
                        break;

                    case 5:
                        molFlowH2Syngas = massValue / (H_MW * 2.0);
                        break;

                    case 6:
                        molFlowH2OSyngas = massValue / (H_MW * 2 + O_MW);
                        break;

                    case 7:
                        molFlowH2SSyngas = massValue / (H_MW * 2 + S_MW);
                        break;

                    case 8:
                        molFlowHClSyngas = massValue / (H_MW + CL_MW);
                        break;

                    case 9:
                        molFlowN2Syngas = massValue / (N_MW * 2);
                        break;

                    case 10:
                        molFlowNH3Syngas = massValue / (N_MW + H_MW * 3);
                        break;

                    case 11:
                        molFlowNOSyngas = massValue / (N_MW + O_MW);
                        break;

                    case 13:
                        molFlowO2Syngas = massValue / (O_MW * 2);
                        break;

                    case 15:
                        molFlowSO2Syngas = massValue / (S_MW + 2 * O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        sumDeltaHRing2 = sumDeltaHRing2B4Combustion + deltaHCombustionRing1 + deltaHCombustionRing2;
        tempGuessK = 1500.0;
        // Temperature iteration before reactions
        while (true) {

            cpN2Guess = (ALPHA_N2 * (tempGuessK - 298) + (BETA_N2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + (GAMMA_N2) / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpO2Guess = (ALPHA_O2 * (tempGuessK - 298) + (BETA_O2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_O2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpArGuess = (ALPHA_AR * (tempGuessK - 298) + (BETA_AR) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_AR / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2OGuess = (ALPHA_H2O * (tempGuessK - 298) + (BETA_H2O) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2O / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCO2Guess = (ALPHA_CO2 * (tempGuessK - 298) + (BETA_CO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCOGuess = (ALPHA_CO * (tempGuessK - 298) + (BETA_CO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCH4Guess = (ALPHA_CH4 * (tempGuessK - 298) + (BETA_CH4) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CH4 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpC6H6Guess = (ALPHA_C6H6 * (tempGuessK - 298) + (BETA_C6H6) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_C6H6 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2Guess = (ALPHA_H2 * (tempGuessK - 298) + (BETA_H2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpHClGuess = (ALPHA_HCL * (tempGuessK - 298) + (BETA_HCL) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_HCL / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2SlGuess = (ALPHA_H2S * (tempGuessK - 298) + (BETA_H2S) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2S / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpSO2lGuess = (ALPHA_SO2 * (tempGuessK - 298) + (BETA_SO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_SO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNH3lGuess = (ALPHA_NH3 * (tempGuessK - 298) + (BETA_NH3) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NH3 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNOGuess = (ALPHA_NO * (tempGuessK - 298) + (BETA_NO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);

            tempGuessF = tempGuessK * 9 / 5. - 459.67;

            guessHeat = (cpN2Guess * molFlowN2Syngas + cpO2Guess * molFlowO2Syngas
                    + cpArGuess * molFlowArSyngas + cpH2OGuess * molFlowH2OSyngas + cpCO2Guess * molFlowCO2Syngas + cpCOGuess * molFlowCOSyngas
                    + cpCH4Guess * molFlowCH4Syngas + cpC6H6Guess * molFlowC6H6Syngas + cpH2Guess * molFlowH2Syngas + cpH2SlGuess * molFlowH2SSyngas
                    + cpNH3lGuess * molFlowNH3Syngas + cpHClGuess * molFlowHClSyngas + cpNOGuess * molFlowNOSyngas + cpSO2lGuess * molFlowSO2Syngas) * (tempGuessF - 77);

            sumNCp = guessHeat / (tempGuessF - 77);

            tempGuessF = 77 + sumDeltaHRing2 / sumNCp;

            tempGuessK = (tempGuessF + 459.67) * 5 / 9.;

            delta = sumDeltaHRing2 - guessHeat;

            //If delta==0, then exit loop
            if (delta < EPSILON) {
                break;
            }

        }

        for (int y = 0; y < numSpecies; y++) {
            massFractions[y] = massFractionsB4[y] + "";
        }
        
        convertMassFractionToMol(true);

        readTemp = tempGuessK + "";
        
        finalTime = timeB4Final;
        
        double heatLossCoefficient = Double.parseDouble((String) textFieldHash.get("tempLossPerFoot"));
        double ambientTemp = Double.parseDouble((String) textFieldHash.get("ambientTemp"));
        
        double readTempVal = Double.parseDouble(readTemp);
        
        if(deltaHCombustionRing2>heatLossCoefficient*outerArea*(readTempVal-ambientTemp)){
        
            readTempVal = readTempVal - (timeAfter - startTime)*(deltaHCombustionRing2 - heatLossCoefficient*outerArea*(readTempVal - ambientTemp))/sumNCp;

        }
        else{
            readTempVal = readTempVal - heatLossCoefficient*outerArea*(readTempVal-ambientTemp);
        }
        
        readTemp = readTempVal + "";

        calculateVelocity();

    }

    private void doEnergyBalance3() {  //new from luis

        
        int speciesIndex, preAirIndex = 0;
        double massValue, delta;

        for (speciesIndex = 0; speciesIndex < numSpecies; speciesIndex++) {

            //Mutiply the current mass fraction with the mass flow rate of the synthetic gas
            massValue = (Double.parseDouble(massFractions[speciesIndex]) * (totalMassFlowRateAfterAmmoniaInj));

            //If the current species is one we are trying to balance...
            if ((preAirIndex < speciesPreAir.length) && speciesNames[speciesIndex].matches(speciesPreAir[preAirIndex])) {

                //index values for species of interest
                //speciesPreAir = {"AR", "C6H6", "CH4", "CO", "CO2", "H2", "H2O", "H2S", "HCL", "N2", "NH3", "NO", "NO2", "O2", "SO", "SO2"};
                //indices: AR=0, C6H6=1, CH4=2, CO=3, CO2=4, H2=5, H2O=6, H2S=7, HCL=8, N2=9, NH3=10, NO=11, NO2=12, O2=13, SO=14, SO2=15
                switch (preAirIndex) {

                    case 0:
                        molFlowArSyngas = massValue / AR_MW;
                        break;

                    case 1:
                        molFlowC6H6Syngas = massValue / (C_MW * 6.0 + H_MW * 6);
                        break;

                    case 2:
                        molFlowCH4Syngas = massValue / (C_MW + H_MW * 4.0);
                        break;

                    case 3:
                        molFlowCOSyngas = massValue / (C_MW + O_MW);
                        break;

                    case 4:
                        molFlowCO2Syngas = massValue / (C_MW + O_MW * 2.0);
                        break;

                    case 5:
                        molFlowH2Syngas = massValue / (H_MW * 2.0);
                        break;

                    case 6:
                        molFlowH2OSyngas = massValue / (H_MW * 2 + O_MW);
                        break;

                    case 7:
                        molFlowH2SSyngas = massValue / (H_MW * 2 + S_MW);
                        break;

                    case 8:
                        molFlowHClSyngas = massValue / (H_MW + CL_MW);
                        break;

                    case 9:
                        molFlowN2Syngas = massValue / (N_MW * 2);
                        break;

                    case 10:
                        molFlowNH3Syngas = massValue / (N_MW + H_MW * 3);
                        break;

                    case 11:
                        molFlowNOSyngas = massValue / (N_MW + O_MW);
                        break;

                    case 13:
                        molFlowO2Syngas = massValue / (O_MW * 2);
                        break;

                    case 15:
                        molFlowSO2Syngas = massValue / (S_MW + 2 * O_MW);
                        break;

                    default:
                        break;
                }
                preAirIndex++;
            }

        }

        sumDeltaHAmmoniaInj = sumDeltaB4HAmmoniaInj + deltaHCombustionRing1+deltaHCombustionRing2 - ammoniaInjMassFlow * 1050;
        
        tempGuessK = 1500.0;
        
        // Temperature iteration before reactions
        while (true) {

            cpN2Guess = (ALPHA_N2 * (tempGuessK - 298) + (BETA_N2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + (GAMMA_N2) / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpO2Guess = (ALPHA_O2 * (tempGuessK - 298) + (BETA_O2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_O2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpArGuess = (ALPHA_AR * (tempGuessK - 298) + (BETA_AR) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_AR / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2OGuess = (ALPHA_H2O * (tempGuessK - 298) + (BETA_H2O) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2O / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCO2Guess = (ALPHA_CO2 * (tempGuessK - 298) + (BETA_CO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCOGuess = (ALPHA_CO * (tempGuessK - 298) + (BETA_CO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpCH4Guess = (ALPHA_CH4 * (tempGuessK - 298) + (BETA_CH4) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_CH4 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpC6H6Guess = (ALPHA_C6H6 * (tempGuessK - 298) + (BETA_C6H6) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_C6H6 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2Guess = (ALPHA_H2 * (tempGuessK - 298) + (BETA_H2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpHClGuess = (ALPHA_HCL * (tempGuessK - 298) + (BETA_HCL) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_HCL / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpH2SlGuess = (ALPHA_H2S * (tempGuessK - 298) + (BETA_H2S) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_H2S / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpSO2lGuess = (ALPHA_SO2 * (tempGuessK - 298) + (BETA_SO2) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_SO2 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNH3lGuess = (ALPHA_NH3 * (tempGuessK - 298) + (BETA_NH3) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NH3 / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);
            cpNOGuess = (ALPHA_NO * (tempGuessK - 298) + (BETA_NO) / 2 * (tempGuessK * tempGuessK - 298 * 298) + GAMMA_NO / 3 * (tempGuessK * tempGuessK * tempGuessK - 298 * 298 * 298)) / (tempGuessK - 298);

            tempGuessF = tempGuessK * 9 / 5. - 459.67;

            guessHeat = (cpN2Guess * molFlowN2Syngas + cpO2Guess * molFlowO2Syngas
                    + cpArGuess * molFlowArSyngas + cpH2OGuess * molFlowH2OSyngas + cpCO2Guess * molFlowCO2Syngas + cpCOGuess * molFlowCOSyngas
                    + cpCH4Guess * molFlowCH4Syngas + cpC6H6Guess * molFlowC6H6Syngas + cpH2Guess * molFlowH2Syngas + cpH2SlGuess * molFlowH2SSyngas
                    + cpNH3lGuess * molFlowNH3Syngas + cpHClGuess * molFlowHClSyngas + cpNOGuess * molFlowNOSyngas + cpSO2lGuess * molFlowSO2Syngas) * (tempGuessF - 77);

            sumNCp = guessHeat / (tempGuessF - 77);

            tempGuessF = 77 + sumDeltaHAmmoniaInj / sumNCp;

            tempGuessK = (tempGuessF + 459.67) * 5 / 9.;

            delta = sumDeltaHAmmoniaInj - guessHeat;
            
            System.out.println("sumDeltaHAmmoniaInj = " + sumDeltaHAmmoniaInj);
            System.out.println("sumDeltaHRing2 = " + sumDeltaHRing2);

            //If delta==0, then exit loop
            if (delta < EPSILON) {
                break;
            }

        }

        readTemp = (tempGuessF + 459.67) * 5 / 9.0 + ""; // degrees Kelvin
        
        double heatLossCoefficient = Double.parseDouble((String) textFieldHash.get("tempLossPerFoot"));
        double currentTempF, totalHeat;
        
        double ambientTemp = Double.parseDouble((String) textFieldHash.get("ambientTemp"));
        
        currentTempF = Double.parseDouble(readTemp) * 9 / 5.0 - 459.67;
        totalHeat = heatLossCoefficient * outerArea * (currentTempF - ambientTemp); //Btu/hr
        currentTempF = currentTempF - (totalHeat / sumNCpGas);
        
        readTemp = (currentTempF + 459.67) * 5 / 9.0 + "";
        
        calculateVelocity();
        
        
    }

    private void grabFinalOutputs(int index) {

        Scanner outputFile;
        String scanLine;
        String lastWord;
        String[] parts;
        String[] vals = new String[numSpecies];
        int currentSpecies = 1;

        try {

            outputFile = new Scanner(new FileReader("CHEMKED\\outputFile_" + index + ".txt"));

            while (outputFile.hasNextLine()) {
                scanLine = outputFile.nextLine();
                if (scanLine.matches(" THERMO")) {
                    break;
                }
            }

            // This while loop is grabbing the incorrect mass fractions. This is supposed to grab
            // the mass fractions that chemked outputs after the initial concentrations have been
            // inputted and then ChemKed ran.
            while (currentSpecies < (numSpecies + 1)) {

                scanLine = outputFile.nextLine();

                if (scanLine.contains(" " + currentSpecies + " ")) {
                    parts = scanLine.split(" ");
                    lastWord = parts[parts.length - 1];
                    vals[currentSpecies - 1] = lastWord;
                    currentSpecies++;
                }

            }

            outputFile.close();

            int m = 0;

            //for all species convert from mass frac to mol and then save it in the desired format
            while (m < numSpecies) {

                finalMolFractions[index - 1][m] = vals[m];
                m++;
            }


        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    private void getValues() {

        tempSyngasF = Double.parseDouble((String) textFieldHash.get("synTempF"));
        tempSyngasK = (tempSyngasF + 459.67) * 5 / 9.;
        //Get the length and diameter of the thermal oxidizer
        toLength = Double.parseDouble((String) textFieldHash.get("thermOxLength"));
        toDiameter = Double.parseDouble((String) textFieldHash.get("thermOxDiameter"));

        //get ring1, ring2, and ammonia injection site locations
        ring1dist = Double.parseDouble((String) textFieldHash.get("ring1distanceTF"));
        ring2dist = Double.parseDouble((String) textFieldHash.get("ring2distanceTF"));
        nh3InjSite = Double.parseDouble((String) textFieldHash.get("ammoniaInjectionSiteTF"));

        //Get the length increments to be used
        deltaIncrement = Double.parseDouble((String) textFieldHash.get("thermOxLengthInc"));

        //Set the current reactor laction to increment so that we dont start at 0
        reactorLocation = deltaIncrement;

        //Divide the length by length increment to get the number of locations we run in the TO, then
        //round that value up to get the remaining outputs
        toRuns = toLength / deltaIncrement;
        toRuns = Math.ceil(toRuns);
        toRunsInt = (int) toRuns;
        toLengthInt = (int) toLength;
        //Obtain all pertinent massflow rates
        syngasMassFlowRate = Double.parseDouble((String) textFieldHash.get("syngasMassFlow"));
        airMassFlowRateRing1 = Double.parseDouble((String) textFieldHash.get("massAirRing1"));
        airMassFlowRateRing2 = Double.parseDouble((String) textFieldHash.get("massAirRing2"));
        flueGasMassFlowRateRing1 = Double.parseDouble((String) textFieldHash.get("massAirFlue1"));
        flueGasMassFlowRateRing2 = Double.parseDouble((String) textFieldHash.get("massAirFlue2"));
        ammoniaInjMassFlow = Double.parseDouble((String) textFieldHash.get("ammoniaInjectMassFlow"));

        //Calculate total mass flowrates for ring 1, 2, and 3
        totalMassFlowRateAfterRing1 = syngasMassFlowRate + airMassFlowRateRing1
                + flueGasMassFlowRateRing1;
        totalMassFlowRateAfterRing2 = totalMassFlowRateAfterRing1
                + airMassFlowRateRing2 + flueGasMassFlowRateRing2;
        totalMassFlowRateAfterAmmoniaInj = totalMassFlowRateAfterRing2
                + ammoniaInjMassFlow;
        
        System.out.println("totalMassFlowRateAfterAmmoniaInj = " + totalMassFlowRateAfterAmmoniaInj);

        massFlowRateN2InAirRing1 = massN2InAir * airMassFlowRateRing1;
        massFlowRateArInAirRing1 = massArInAir * airMassFlowRateRing1;
        massFlowRateH2OInAirRing1 = massH2OInAir * airMassFlowRateRing1;
        massFlowRateO2InAirRing1 = massO2InAir * airMassFlowRateRing1;

        massFlowRateN2InAirRing2 = massN2InAir * airMassFlowRateRing2;
        massFlowRateArInAirRing2 = massArInAir * airMassFlowRateRing2;
        massFlowRateH2OInAirRing2 = massH2OInAir * airMassFlowRateRing2;
        massFlowRateO2InAirRing2 = massO2InAir * airMassFlowRateRing2;

        double tempAirF = Double.parseDouble((String) textFieldHash.get("airTempF"));
        double tempAirK = 5. / 9 * (tempAirF + 459.67);
        double cpN2InAir, cpO2InAir, cpArInAir, cpH2OInAir, sumNCpAirRing1, sumNCpAirRing2;

        if (tempAirK - 298 > EPSILON) {
            cpN2InAir = (ALPHA_N2 * tempAirK + BETA_N2 / 2 * (tempAirK * tempAirK - 298 * 298)
                    + GAMMA_N2 / 3 * (tempAirK * tempAirK * tempAirK - 298 * 298 * 298)) / (tempAirK - 298);
            cpO2InAir = (ALPHA_O2 * tempAirK + BETA_O2 / 2 * (tempAirK * tempAirK - 298 * 298)
                    + GAMMA_O2 / 3 * (tempAirK * tempAirK * tempAirK - 298 * 298 * 298)) / (tempAirK - 298);
            cpArInAir = (ALPHA_AR * tempAirK + BETA_AR / 2 * (tempAirK * tempAirK - 298 * 298)
                    + GAMMA_AR / 3 * (tempAirK * tempAirK * tempAirK - 298 * 298 * 298)) / (tempAirK - 298);
            cpH2OInAir = (ALPHA_H2O * tempAirK + BETA_H2O / 2 * (tempAirK * tempAirK - 298 * 298)
                    + GAMMA_H2O / 3 * (tempAirK * tempAirK * tempAirK - 298 * 298 * 298)) / (tempAirK - 298);

            sumNCpAirRing1 = cpN2InAir * massFlowRateN2InAirRing1 / (2 * N_MW)
                    + cpO2InAir * massFlowRateO2InAirRing1 / (2 * O_MW)
                    + cpArInAir * massFlowRateArInAirRing1 / (AR_MW)
                    + cpH2OInAir * massFlowRateH2OInAirRing1 / (2 * H_MW + O_MW);

            sumNCpAirRing2 = cpN2InAir * massFlowRateN2InAirRing2 / (2 * N_MW)
                    + cpO2InAir * massFlowRateO2InAirRing2 / (2 * O_MW)
                    + cpArInAir * massFlowRateArInAirRing2 / (AR_MW)
                    + cpH2OInAir * massFlowRateH2OInAirRing2 / (2 * H_MW + O_MW);


            deltaHAirRing1 = sumNCpAirRing1 * (tempAirF - 77); // Btu/hr
            deltaHAirRing2 = sumNCpAirRing2 * (tempAirF - 77); // Btu/hr

        } else {
            deltaHAirRing1 = 0;
            deltaHAirRing2 = 0;
        }

        double molN2InFlueTimesMW, molArInFlueTimesMW, molO2InFlueTimesMW,
                molH2OInFlueTimesMW, molCO2InFlueTimesMW, sumMolsTimesMWInFlue,
                massFractionN2InFlue, massFractionArInFlue,
                massFractionO2InFlue, massFractionH2OInFlue,
                massFractionCO2InFlue;

        // This code multiplies the mol fraction of the composition of flue gas
        // by its MW. Then sums those multiples into a variable. Then each individual
        // mol fraction times its MW is divided by its sum (i.e. Xi =Yi*MW/sum(Yi*MW))
        molN2InFlueTimesMW = Double.parseDouble((String) textFieldHash.get("flueGasN2")) * 2 * N_MW;
        molArInFlueTimesMW = Double.parseDouble((String) textFieldHash.get("flueGasAr")) * AR_MW;
        molO2InFlueTimesMW = Double.parseDouble((String) textFieldHash.get("flueGasO2")) * 2 * O_MW;
        molH2OInFlueTimesMW = Double.parseDouble((String) textFieldHash.get("flueGasH2O")) * (2 * H_MW + O_MW);
        molCO2InFlueTimesMW = Double.parseDouble((String) textFieldHash.get("flueGasCO2")) * (C_MW + 2 * O_MW);
        sumMolsTimesMWInFlue = molN2InFlueTimesMW + molArInFlueTimesMW
                + molO2InFlueTimesMW + molO2InFlueTimesMW + molH2OInFlueTimesMW
                + molCO2InFlueTimesMW;
        massFractionN2InFlue = molN2InFlueTimesMW / sumMolsTimesMWInFlue;
        massFractionArInFlue = molArInFlueTimesMW / sumMolsTimesMWInFlue;
        massFractionO2InFlue = molO2InFlueTimesMW / sumMolsTimesMWInFlue;
        massFractionH2OInFlue = molH2OInFlueTimesMW / sumMolsTimesMWInFlue;
        massFractionCO2InFlue = molCO2InFlueTimesMW / sumMolsTimesMWInFlue;

        massFlowRateN2InFlueGasRing1 = massFractionN2InFlue * flueMassFlowRateRing1;
        massFlowRateArInFlueGasRing1 = massFractionArInFlue * flueMassFlowRateRing1;
        massFlowRateO2InFlueGasRing1 = massFractionO2InFlue * flueMassFlowRateRing1;
        massFlowRateH2OInFlueGasRing1 = massFractionH2OInFlue * flueMassFlowRateRing1;
        massFlowRateCO2InFlueGasRing1 = massFractionCO2InFlue * flueMassFlowRateRing1;

        massFlowRateN2InFlueGasRing2 = massFractionN2InFlue * flueMassFlowRateRing2;
        massFlowRateArInFlueGasRing2 = massFractionArInFlue * flueMassFlowRateRing2;
        massFlowRateO2InFlueGasRing2 = massFractionO2InFlue * flueMassFlowRateRing2;
        massFlowRateH2OInFlueGasRing2 = massFractionH2OInFlue * flueMassFlowRateRing2;
        massFlowRateCO2InFlueGasRing2 = massFractionCO2InFlue * flueMassFlowRateRing2;

        // converting temperature from a string to a double and subtracting the reference temp of 77 F 
        double tempFlueF = Double.parseDouble((String) textFieldHash.get("flueTempF"));
        double tempFlueK = 5 / 9 * (tempFlueF + 459.67);

        double cpN2InFlueGas, cpO2InFlueGas, cpArInFlueGas,
                cpH2OInFlueGas, cpCO2InFlueGas;


        if (tempFlueK - 298 > EPSILON) {
            cpN2InFlueGas = (ALPHA_N2 * tempFlueK + BETA_N2 / 2 * (tempFlueK * tempFlueK - 298 * 298)
                    + GAMMA_N2 / 3 * (tempFlueK * tempFlueK * tempFlueK - 298 * 298 * 298)) / (tempFlueK - 298);
            cpO2InFlueGas = (ALPHA_O2 * tempFlueK + BETA_O2 / 2 * (tempFlueK * tempFlueK - 298 * 298)
                    + GAMMA_O2 / 3 * (tempFlueK * tempFlueK * tempFlueK - 298 * 298 * 298)) / (tempFlueK - 298);
            cpArInFlueGas = (ALPHA_AR * tempFlueK + BETA_AR / 2 * (tempFlueK * tempFlueK - 298 * 298)
                    + GAMMA_AR / 3 * (tempFlueK * tempFlueK * tempFlueK - 298 * 298 * 298)) / (tempFlueK - 298);
            cpH2OInFlueGas = (ALPHA_H2O * tempFlueK + BETA_H2O / 2 * (tempFlueK * tempFlueK - 298 * 298)
                    + GAMMA_H2O / 3 * (tempFlueK * tempFlueK * tempFlueK - 298 * 298 * 298)) / (tempFlueK - 298);
            cpCO2InFlueGas = (ALPHA_CO2 * tempFlueK + BETA_CO2 / 2 * (tempFlueK * tempFlueK - 298 * 298)
                    + GAMMA_CO2 / 3 * (tempFlueK * tempFlueK * tempFlueK - 298 * 298 * 298)) / (tempFlueK - 298);

            sumNCpInFlueGasRing1 = cpN2InFlueGas * massFlowRateN2InFlueGasRing1 / (2 * N_MW)
                    + cpO2InFlueGas * massFlowRateO2InFlueGasRing1 / (2 * O_MW)
                    + cpArInFlueGas * massFlowRateArInFlueGasRing1 / (AR_MW)
                    + cpH2OInFlueGas * massFlowRateH2OInFlueGasRing1 / (2 * H_MW + O_MW)
                    + cpCO2InFlueGas * massFlowRateCO2InFlueGasRing1 / (2 * C_MW + O_MW);

            sumNCpInFlueGasRing2 = cpN2InFlueGas * massFlowRateN2InFlueGasRing2 / (2 * N_MW)
                    + cpO2InFlueGas * massFlowRateO2InFlueGasRing2 / (2 * O_MW)
                    + cpArInFlueGas * massFlowRateArInFlueGasRing2 / (AR_MW)
                    + cpH2OInFlueGas * massFlowRateH2OInFlueGasRing2 / (2 * H_MW + O_MW)
                    + cpCO2InFlueGas * massFlowRateCO2InFlueGasRing2 / (2 * C_MW + O_MW);


            deltaHFlueGasRing1 = sumNCpInFlueGasRing1 * (tempFlueF - 77); // Btu/hr
            deltaHFlueGasRing2 = sumNCpInFlueGasRing2 * (tempFlueF - 77); // Btu/hr

        } else {
            deltaHFlueGasRing1 = 0;
            deltaHFlueGasRing2 = 0;
        }

        double tempSynF = Double.parseDouble((String) textFieldHash.get("synTempF")); // Degrees Kelvin   
        double tempSynK = (tempSynF + 459.67) * 5. / 9;
        double tempSyn = tempSynK - 298, tempSyn2 = tempSynK * tempSynK - 298 * 298,
                tempSyn3 = tempSynK * tempSynK * tempSynK - 298 * 298 * 298;

        cpN2InSyngas = (ALPHA_N2 * tempSyn + (BETA_N2) / 2 * tempSyn2 + (GAMMA_N2) / 3 * tempSyn3) / tempSyn;
        cpO2InSyngas = (ALPHA_O2 * tempSyn + (BETA_O2) / 2 * tempSyn2 + GAMMA_O2 / 3 * tempSyn3) / tempSyn;
        cpArInSyngas = (ALPHA_AR * tempSyn + (BETA_AR) / 2 * tempSyn2 + GAMMA_AR / 3 * tempSyn3) / tempSyn;
        cpH2OInSyngas = (ALPHA_H2O * tempSyn + (BETA_H2O) / 2 * tempSyn2 + GAMMA_H2O / 3 * tempSyn3) / tempSyn;
        cpCO2InSyngas = (ALPHA_CO2 * tempSyn + (BETA_CO2) / 2 * tempSyn2 + GAMMA_CO2 / 3 * tempSyn3) / tempSyn;
        cpCOInSyngas = (ALPHA_CO * tempSyn + (BETA_CO) / 2 * tempSyn2 + GAMMA_CO / 3 * tempSyn3) / tempSyn;
        cpCH4InSyngas = (ALPHA_CH4 * tempSyn + (BETA_CH4) / 2 * tempSyn2 + GAMMA_CH4 / 3 * tempSyn3) / tempSyn;
        cpC6H6InSyngas = (ALPHA_C6H6 * tempSyn + (BETA_C6H6) / 2 * tempSyn2 + GAMMA_C6H6 / 3 * tempSyn3) / tempSyn;
        cpH2InSyngas = (ALPHA_H2 * tempSyn + (BETA_H2) / 2 * tempSyn2 + GAMMA_H2 / 3 * tempSyn3) / tempSyn;
        cpHClInSyngas = (ALPHA_HCL * tempSyn + (BETA_HCL) / 2 * tempSyn2 + GAMMA_HCL / 3 * tempSyn3) / tempSyn;
        cpH2SInSyngas = (ALPHA_H2S * tempSyn + (BETA_H2S) / 2 * tempSyn2 + GAMMA_H2S / 3 * tempSyn3) / tempSyn;
        cpSO2InSyngas = (ALPHA_SO2 * tempSyn + (BETA_SO2) / 2 * tempSyn2 + GAMMA_SO2 / 3 * tempSyn3) / tempSyn;
        cpNH3InSyngas = (ALPHA_NH3 * tempSyn + (BETA_NH3) / 2 * tempSyn2 + GAMMA_NH3 / 3 * tempSyn3) / tempSyn;
        cpNOInSyngas = (ALPHA_NO * tempSyn + (BETA_NO) / 2 * tempSyn2 + GAMMA_NO / 3 * tempSyn3) / tempSyn;
        // CP values in Btu/lbmol-F or cal/gmol-C

        double molFractionTimesMWArSyn = Double.parseDouble((String) textFieldHash.get("ARsyn")) * AR_MW;
        double molFractionTimesMWC6H6Syn = Double.parseDouble((String) textFieldHash.get("C6H6syn")) * (6 * C_MW + 6 * H_MW);
        double molFractionTimesMWCH4Syn = Double.parseDouble((String) textFieldHash.get("CH4syn")) * (C_MW + 4 * H_MW);
        double molFractionTimesMWCOSyn = Double.parseDouble((String) textFieldHash.get("COsyn")) * (C_MW + O_MW);
        double molFractionTimesMWCO2Syn = Double.parseDouble((String) textFieldHash.get("CO2syn")) * (C_MW + 2 * O_MW);
        double molFractionTimesMWH2Syn = Double.parseDouble((String) textFieldHash.get("H2syn")) * 2 * H_MW;
        double molFractionTimesMWH2OSyn = Double.parseDouble((String) textFieldHash.get("H2Osyn")) * (H_MW * 2 + O_MW);
        double molFractionTimesMWH2SSyn = Double.parseDouble((String) textFieldHash.get("H2Ssyn")) * (2 * H_MW + S_MW);
        double molFractionTimesMWHClSyn = Double.parseDouble((String) textFieldHash.get("HCLsyn")) * (H_MW + CL_MW);
        double molFractionTimesMWN2Syn = Double.parseDouble((String) textFieldHash.get("N2syn")) * 2 * N_MW;
        double molFractionTimesMWNH3Syn = Double.parseDouble((String) textFieldHash.get("NH3syn")) * (N_MW + 3 * H_MW);
        double molFractionTimesMWO2Syn = Double.parseDouble((String) textFieldHash.get("O2syn")) * 2 * O_MW;
        double sumMolFractionTimesMWSyn = molFractionTimesMWArSyn + molFractionTimesMWC6H6Syn
                + molFractionTimesMWCH4Syn + molFractionTimesMWCOSyn
                + molFractionTimesMWCO2Syn + molFractionTimesMWH2Syn
                + molFractionTimesMWH2OSyn + molFractionTimesMWH2SSyn
                + molFractionTimesMWHClSyn + molFractionTimesMWN2Syn
                + molFractionTimesMWNH3Syn + molFractionTimesMWO2Syn;
        double massFractionArSyn = molFractionTimesMWArSyn / sumMolFractionTimesMWSyn;
        double massFractionC6H6Syn = molFractionTimesMWC6H6Syn / sumMolFractionTimesMWSyn;
        double massFractionCH4Syn = molFractionTimesMWCH4Syn / sumMolFractionTimesMWSyn;
        double massFractionCOSyn = molFractionTimesMWCOSyn / sumMolFractionTimesMWSyn;
        double massFractionCO2Syn = molFractionTimesMWCO2Syn / sumMolFractionTimesMWSyn;
        double massFractionH2Syn = molFractionTimesMWH2Syn / sumMolFractionTimesMWSyn;
        double massFractionH2OSyn = molFractionTimesMWH2OSyn / sumMolFractionTimesMWSyn;
        double massFractionH2SSyn = molFractionTimesMWH2SSyn / sumMolFractionTimesMWSyn;
        double massFractionHClSyn = molFractionTimesMWHClSyn / sumMolFractionTimesMWSyn;
        double massFractionN2Syn = molFractionTimesMWN2Syn / sumMolFractionTimesMWSyn;
        double massFractionNH3Syn = molFractionTimesMWNH3Syn / sumMolFractionTimesMWSyn;
        double massFractionO2Syn = molFractionTimesMWO2Syn / sumMolFractionTimesMWSyn;
        molFlowRateSyngas = syngasMassFlowRate * (massFractionArSyn / AR_MW
                + massFractionC6H6Syn / (6 * C_MW + 6 * H_MW) + massFractionCH4Syn / (C_MW + 4 * H_MW)
                + massFractionCOSyn / (C_MW + O_MW) + massFractionCO2Syn / (C_MW + 2 * O_MW)
                + massFractionH2Syn / (2 * H_MW) + massFractionH2OSyn / (2 * H_MW + O_MW)
                + massFractionH2SSyn / (2 * H_MW + S_MW) + massFractionHClSyn / (H_MW + CL_MW)
                + massFractionN2Syn / (2 * N_MW) + massFractionNH3Syn / (N_MW + 3 * H_MW)
                + massFractionO2Syn / (O_MW * 2));

        sumNCpInSyngas = syngasMassFlowRate * (massFractionArSyn * cpArInSyngas / AR_MW
                + massFractionC6H6Syn * cpArInSyngas / (6 * C_MW + 6 * H_MW) + massFractionCH4Syn * cpArInSyngas / (C_MW + 4 * H_MW)
                + massFractionCOSyn * cpArInSyngas / (C_MW + O_MW) + massFractionCO2Syn * cpArInSyngas / (C_MW + 2 * O_MW)
                + massFractionH2Syn * cpArInSyngas / (2 * H_MW) + massFractionH2OSyn * cpArInSyngas / (2 * H_MW + O_MW)
                + massFractionH2SSyn * cpArInSyngas / (2 * H_MW + S_MW) + massFractionHClSyn * cpArInSyngas / (H_MW + CL_MW)
                + massFractionN2Syn * cpArInSyngas / (2 * N_MW) + massFractionNH3Syn * cpArInSyngas / (N_MW + 3 * H_MW)
                + massFractionO2Syn * cpArInSyngas / (O_MW * 2));
        deltaHSyngas = sumNCpInSyngas * (tempSynF - 77);

        double ammoniaInjH2OMass = Double.parseDouble((String) textFieldHash.get("ammoniaInjectH2O"));
        double ammoniaInjNH3Mass = Double.parseDouble((String) textFieldHash.get("ammoniaInjectNH3"));

        double tempAmmoniaF = Double.parseDouble((String) textFieldHash.get("ammoniaInjectTempF"));
        double tempAmmoniaK = (tempAmmoniaF + 459.67) * 5 / 9.;
        double cpNH3AmmoniaInj, cpH2OAmmoniaInj;

        if (tempAmmoniaF - 77 > EPSILON) {
            cpNH3AmmoniaInj = (ALPHA_NH3 * (tempAmmoniaK - 298) + (BETA_NH3) / 2 * (tempAmmoniaK * tempAmmoniaK - 298 * 298) + GAMMA_NH3 / 3 * (tempAmmoniaK * tempAmmoniaK * tempAmmoniaK - 298 * 298 * 298)) / (tempAmmoniaK - 298);
            cpH2OAmmoniaInj = (ALPHA_H2O * (tempAmmoniaK - 298) + (BETA_H2O) / 2 * (tempAmmoniaK * tempAmmoniaK - 298 * 298) + GAMMA_H2O / 3 * (tempAmmoniaK * tempAmmoniaK * tempAmmoniaK - 298 * 298 * 298)) / (tempAmmoniaK - 298);
            sumNCpInAmmoniaInj = (cpNH3AmmoniaInj * ammoniaInjNH3Mass * ammoniaInjMassFlow / (N_MW + H_MW * 3)
                    + cpH2OAmmoniaInj * ammoniaInjH2OMass * ammoniaInjMassFlow / (H_MW * 2 + O_MW));
            deltaHAmmoniaInj = sumNCpInAmmoniaInj * (tempAmmoniaF - 77); //BTU/hr
        } else {
            deltaHAmmoniaInj = 0;
        }


        sumDeltaHRing1B4Combustion = deltaHAirRing1 + deltaHFlueGasRing1 + deltaHSyngas;//BTU/hr
        sumDeltaHRing2B4Combustion = sumDeltaHRing1B4Combustion + deltaHAirRing2
                + deltaHFlueGasRing2;//BTU/hr
        
        sumDeltaB4HAmmoniaInj = deltaHAmmoniaInj + sumDeltaHRing2B4Combustion;

    }
    
    
} //end of Thermal Oxidizer class
