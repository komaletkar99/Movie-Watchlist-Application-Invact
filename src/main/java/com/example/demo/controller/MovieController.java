package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public String getAllMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "movie-list";
    }

    @GetMapping("/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie-form";
    }

    @PostMapping("/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            model.addAttribute("movie", movie);
            return "movie-form";
        }
        return "redirect:/movies";
    }

    @PostMapping("/edit/{id}")
    public String editMovie(@PathVariable Long id, @ModelAttribute Movie movie) {
        Movie existingMovie = movieService.getMovieById(id);
        if (existingMovie != null) {
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setReleaseYear(movie.getReleaseYear());
            existingMovie.setGenre(movie.getGenre());
            existingMovie.setWatched(movie.isWatched());
            existingMovie.setRating(movie.getRating());
            existingMovie.setReview(movie.getReview());
            movieService.saveMovie(existingMovie);
        }
        return "redirect:/movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/movies";
    }

    @GetMapping("/toggle-watched/{id}")
    public String toggleWatched(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            movie.setWatched(!movie.isWatched());
            movieService.saveMovie(movie);
        }
        return "redirect:/movies";
    }
}
