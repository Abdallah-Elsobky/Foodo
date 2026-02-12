package iti.student.foodo.features.planner.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import iti.student.foodo.core.utils.Animations;
import iti.student.foodo.data.db.pojo.PlannedMealWithDetails;
import iti.student.foodo.data.mapper.MealMapper;
import iti.student.foodo.data.model.domain.Meal;
import iti.student.foodo.databinding.DeleteMealItemBinding;

public class DeleteMealAdapter extends RecyclerView.Adapter<DeleteMealAdapter.ViewHolder> {
    List<PlannedMealWithDetails> meals = List.of();
    MealClickListener listener;

    public DeleteMealAdapter(MealClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeleteMealItemBinding binding = DeleteMealItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlannedMealWithDetails meal = meals.get(position);
        holder.bind(meal, position);
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void submitList(List<PlannedMealWithDetails> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void notifyFavoriteChanged(int position) {
        notifyItemChanged(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        DeleteMealItemBinding binding;
        MealClickListener listener;

        public ViewHolder(DeleteMealItemBinding binding, MealClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(PlannedMealWithDetails plannedMeal, int position) {
            Meal meal = MealMapper.fromEntity(plannedMeal.getMeal());
            binding.tvTitle.setText(meal.getName());
            binding.tvSubtitle.setText(String.format("%s • %s", meal.getCategory() == null ? "" : meal.getCategory(), meal.getArea() == null ? "" : meal.getArea()));
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
                meal.setFav(!meal.isFav());
                Animations.addToFav(v);
                listener.onFavoriteClick(meal);
                notifyItemChanged(position);
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
