package com.example.navegacaotelas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navegacaotelas.scrren.LoginScreen
import com.example.navegacaotelas.scrren.MenuScrren
import com.example.navegacaotelas.scrren.PedidosScreen
import com.example.navegacaotelas.scrren.PerfilScrren
import com.example.navegacaotelas.ui.theme.NavegacaoTelasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoTelasTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "login",

                        // Avançar: a tela atual sai para a esquerda

                        // Avançar: a nova tela entra pela direita
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(400)
                            )
                        },
                        // Voltar: a tela anterior reaparece vindo da esquerda
                        popEnterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(400)
                            )
                        },
                        // Voltar: a tela atual sai para a direita
                        popExitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(400)
                            )
                        }
                    ) {
                        composable(route = "login",exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up, // Efeito de movimento de tela para cima
                                animationSpec = tween(400) // duração em milissegundos
                            ) + fadeOut( // efeito de desaparecer (opacidade vai diminuindo até a tela ficar invisível).
                                animationSpec = tween(1000)
                            )
                        },
                            ) {
                            LoginScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }

                        composable(route = "menu") {
                            MenuScrren(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }

                        // Rota de pedidos e seus parâmetros
                        composable(
                            route = "pedidos?numeroPedido={numeroPedido}",
                            arguments = listOf(
                                navArgument(name = "numeroPedido") {
                                    type = NavType.StringType
                                    defaultValue = "Sem Pedidos"
                                }
                            )
                        ) { backStackEntry ->
                            val numeroPedido = backStackEntry.arguments
                                ?.getString("numeroPedido") ?: "Sem Pedidos"

                            PedidosScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                numeroPedido = numeroPedido
                            )
                        }

                        // Fluxo da tela PERFIL e seus parâmetros
                        composable(
                            route = "perfil/{nome}/{idade}",
                            arguments = listOf(
                                navArgument(name = "nome") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "idade") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val nome = backStackEntry.arguments?.getString("nome") ?: ""
                            val idade = backStackEntry.arguments?.getInt("idade") ?: 0

                            PerfilScrren(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                nome = nome,
                                idade = idade
                            )
                        }
                    }
                }
            }
        }
    }
}