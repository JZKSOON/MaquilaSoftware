package controllers.ViewsAdm;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioViewController implements Initializable {

    @FXML
    private BarChart<String, Number> barChartAvance;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configuración de los ejes
        yAxis.setLabel("Porcentaje");
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(10);

        xAxis.setLabel("Cortes");

        // Datos de ejemplo (esto se conectara a la base de datos)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("121", 12));
        series.getData().add(new XYChart.Data<>("22", 95));
        series.getData().add(new XYChart.Data<>("34", 87));
        series.getData().add(new XYChart.Data<>("56", 77));
        series.getData().add(new XYChart.Data<>("122", 58));
        series.getData().add(new XYChart.Data<>("455", 26));
        series.getData().add(new XYChart.Data<>("733", 20));
        series.getData().add(new XYChart.Data<>("1", 40));
        series.getData().add(new XYChart.Data<>("2", 60));
        series.getData().add(new XYChart.Data<>("3", 99));

        barChartAvance.getStylesheets().add(getClass().getResource("/CSS/ColoresBarrasEstadistico.css").toExternalForm());
        barChartAvance.getData().add(series);
        barChartAvance.setLegendVisible(false);

    }
    /*public void cargarDatosDesdeBD(List<CorteAvanceDTO> datos) {
        barChartAvance.getData().clear(); // Limpiar gráfico anterior
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (CorteAvanceDTO corte : datos) {
            series.getData().add(new XYChart.Data<>(String.valueOf(corte.getNumeroCorte()), corte.getPorcentajeAvance()));
        }

        barChartAvance.getData().add(series);
        barChartAvance.setLegendVisible(false);
    }*/
}
