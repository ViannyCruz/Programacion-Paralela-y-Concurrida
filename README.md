# Programacion-Paralela-y-Concurrida
1. Multiplicación de Matrices con MPI
Este programa realiza la multiplicación de dos matrices cuadradas utilizando programación de MPI (Message Passing Interface). El programa divide las filas de las matrices entre los procesos disponibles y luego recopila los resultados en el proceso raíz.


2. Compilación y Ejecución
Para compilar el programa, necesitará tener instalado un compilador de Java y la biblioteca MPI. Una vez que lo tenga instalados, puede compilar el programa con el siguiente comando:

<path_to_mpj>\bin\mpjrun.bat -np <number_of_processes> -cp . org.example.MatrixMultiplicationMPI

Por ejemplo:
C:\mpj-v0_44\bin\mpjrun.bat -np 4 -cp . org.example.MatrixMultiplicationMPI


3. Ejemplos de Entrada y Salida
El programa genera matrices aleatorias para la multiplicación. Aquí hay algunos ejemplos de salida del programa:

![image](https://github.com/ViannyCruz/Programacion-Paralela-y-Concurrida/assets/113074158/99cad9d7-8c04-4baf-ad7d-d3fb13e86603)

![image](https://github.com/ViannyCruz/Programacion-Paralela-y-Concurrida/assets/113074158/5f7c923c-03fc-4ac7-83af-88a6b83f882b)

![image](https://github.com/ViannyCruz/Programacion-Paralela-y-Concurrida/assets/113074158/0bfe0525-f8d3-4a50-b8dc-097acabe61ec)

Para matriz 100 x 100
![image](https://github.com/ViannyCruz/Programacion-Paralela-y-Concurrida/assets/113074158/b440f0b0-6392-44fc-9b63-1c3adad55ff4)







Para confirmar los cálculos del programa se utilizó la herramienta online matrixcalc ( https://matrixcalc.org/es/ ), y para ver la diferencia entre los tiempos para un programa secuencial y otro que implementa multiplicación paralela dentro del github tenemos tanto la versión paralela como la secuencial.


4. Análisis de Rendimiento
El rendimiento del programa de multiplicación de matrices utilizando MPI mejora a medida que se incrementa el número de procesos, hasta cierto punto. Con dos procesos, el tiempo promedio es de 29.2 ms. Con cuatro procesos, el tiempo promedio aumenta ligeramente a 33.5 ms. Sin embargo, con seis procesos, el tiempo promedio se reduce a 20.9 ms.

Este análisis muestra que la eficiencia de paralelización puede variar dependiendo del número de procesos utilizados. En este caso, seis procesos ofrecen el mejor rendimiento para la multiplicación de matrices 100 x 100. Es importante considerar la sobrecarga de comunicación entre procesos al aumentar el número de procesos, lo cual puede explicar el aumento de tiempo observado con cuatro procesos en comparación con dos.

Cálculos:
Para 2 procesos y dos matrices 100 x 100
Ejecute el programa 10 veces y en términos de tiempo estos fueron los resultados:
1. 39 ms
2. 31 ms
3. 23 ms
4.40 ms
5. 34 ms
6. 19 ms
7. 21 ms
8. 33 ms
9. 28 ms
10. 24 ms

292 / 10 = 29.2 ms

Entonces para calcular una multiplicación de dos matrices 100 x 100 aproximadamente le toma 33.5 ms con cuatro procesos. 



Para 4 procesos y dos matrices 100 x 100
Ejecute el programa 10 veces y en términos de tiempo estos fueron los resultados:
1. 44 ms
2. 28 ms
3. 46 ms
4. 31 ms
5. 34 ms
6. 35 ms
7. 23 ms
8. 29 ms
9. 32 ms
10. 33 ms

335 / 10 = 33.5 ms

Entonces para calcular una multiplicación de dos matrices 100 x 100 aproximadamente le toma 33.5 ms con cuatro procesos. 



Para 6 procesos y dos matrices 100 x 100
Ejecute el programa 10 veces y en términos de tiempo estos fueron los resultados:
1. 21 ms
2. 21 ms
3. 27 ms
4. 21 ms
5. 20 ms
6. 19 ms
7. 22 ms
8. 21 ms
9. 19 ms
10. 18 ms

209 / 10 = 20.9 ms

Entonces para calcular una multiplicación de dos matrices 100 x 100 aproximadamente le toma 20.9 ms a seis procesos. 



