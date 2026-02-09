package iti.student.foodo.features.meal.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import iti.student.foodo.data.model.domain.Ingredient;
import iti.student.foodo.databinding.IngredientItemBinding;

public class MealIngredientAdapter extends RecyclerView.Adapter<MealIngredientAdapter.ViewHolder> {
    IngredientItemBinding binding;
    List<Ingredient> ingredients;
    final OnAddToCartListener onAddToCartListener;

    public MealIngredientAdapter(List<Ingredient> ingredients, OnAddToCartListener onAddToCartListener) {
        this.onAddToCartListener = onAddToCartListener;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = IngredientItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull IngredientItemBinding binding) {
            super(binding.getRoot());
        }

        public void bind(Ingredient ingredient) {
            binding.tvName.setText(ingredient.getName());
            binding.tvAmount.setText(ingredient.getMeasure());
            Glide.with(binding.getRoot().getContext()).load(ingredient.getImage()).thumbnail(0.01f).into(binding.ivIngredient);
            binding.addToCart.setOnClickListener(v -> {
                onAddToCartListener.onAdd(ingredient);
            });
        }
    }

    public interface OnAddToCartListener {
        void onAdd(Ingredient ingredient);
    }
}
