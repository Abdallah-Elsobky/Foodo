package iti.student.foodo.features.home.view;

import static android.view.View.*;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import iti.student.foodo.R;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.databinding.MealItemBinding;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    List<Meal> meals = List.of();
    MealClickListener listener;

    public MealAdapter(MealClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MealItemBinding binding = MealItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(meals.get(position));
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void submitList(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MealItemBinding binding;
        MealClickListener listener;

        public ViewHolder(MealItemBinding binding, MealClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(Meal meal) {
            binding.tvTitle.setText(meal.getName());
            binding.tvSubtitle.setText(String.format("%s • %s", meal.getCategory(), meal.getArea()));
            binding.loading.setVisibility(VISIBLE);
            Glide.with(binding.getRoot())
                    .load(meal.getImage())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            binding.loading.setVisibility(GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            binding.loading.setVisibility(GONE);
                            return false;
                        }
                    })
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.bottom_gradient)
                    .error(R.drawable.bad_response)
                    .into(binding.ivFoodImage);

            binding.btnFavorite.setOnClickListener(v -> {
                listener.onFavoriteClick(meal);
                binding.loveImg.setImageDrawable(ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.fav_ic));
            });

            binding.getRoot().setOnClickListener(v -> {
                listener.onMealClick(meal);
            });
        }
    }

    public interface MealClickListener {
        void onMealClick(Meal meal);

        void onFavoriteClick(Meal meal);
    }
}
