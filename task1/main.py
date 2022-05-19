# ------------------------------------------------------
# ---------------------- main.py -----------------------
# ------------------------------------------------------
from PyQt5.QtWidgets import*
from PyQt5.uic import loadUi
from matplotlib.backends.backend_qt5agg import (NavigationToolbar2QT as NavigationToolbar)
import numpy as np
import utils     
import sys
class MatplotlibWidget(QMainWindow):
    
    def __init__(self):
        QMainWindow.__init__(self)
        loadUi("design1.ui",self)
        self.errs=[]
        self.setWindowTitle("Function Plotter")
        self.ClearValues()
        self.plot.clicked.connect(self.GetValues)
        self.clear.clicked.connect(self.ClearValues)
        self.filter =utils.Filter()
        self.addToolBar(NavigationToolbar(self.MplWidget.canvas, self))
        

    
    def ValidateGraph(self,equation,minX,maxX,labelX="",labelY=""):
        try:
            x = np.linspace(minX,maxX)
            y = eval(equation)
            self.update_graph(x,y,equation,labelX,labelY)
        except:
            self.errs.append("Invalid Equation")
            self.showErrors()



    def update_graph(self,x,y,equation,labelX="",labelY=""):
        '''
        this function responsible for drawing the graph with the given parameters
        '''
        self.MplWidget.canvas.axes.clear()
        self.MplWidget.canvas.axes.plot(x,y)
        self.MplWidget.canvas.axes.legend(("y"),loc='upper right')
        self.MplWidget.canvas.axes.grid()
        self.MplWidget.canvas.axes.set_title("y= "+equation)
        self.MplWidget.canvas.axes.set_xlabel(labelX)
        self.MplWidget.canvas.axes.set_ylabel(labelY)
        self.MplWidget.canvas.draw()

    def GetValues(self):
        '''
        this function responsible for getting the values of equation and values of x,
        call function of filtering and validation
        '''
        self.errs = []
        equation = self.filter.filterEquation(self.eqInput.text())
        minX = self.filter.filterNumbers(self.minX.text(),self.errs)
        maxX = self.filter.filterNumbers(self.maxX.text(),self.errs)
        labelX = self.xlabel.text()
        labelY = self.ylabel.text()
        print(equation)
        if self.errs == []:
            utils.Validation(equation,minX,maxX,self.errs)
        if self.errs != []:
            self.showErrors() 
        else: 
            self.ValidateGraph(equation,minX,maxX,labelX,labelY)

    def initGraph(self):
        self.MplWidget.canvas.axes.clear()
        self.MplWidget.canvas.axes.grid()
        self.MplWidget.canvas.draw()

    def ClearValues(self):
        self.initGraph()
        self.eqInput.setText("")
        self.minX.setText("")
        self.maxX.setText("")

    def showErrors(self):
        msg = QMessageBox()
        msg.setIcon(QMessageBox.Critical)
        msg.setText("Error")
        msg.setInformativeText(self.errs[0])
        msg.setWindowTitle("Error")
        msg.exec_()

        
if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = MatplotlibWidget()
    window.show()
    app.exec_()