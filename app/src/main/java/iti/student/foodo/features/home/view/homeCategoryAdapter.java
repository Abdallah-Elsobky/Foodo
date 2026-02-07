package iti.student.foodo.features.home.view;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.databinding.MainListItemBinding;

public class homeCategoryAdapter extends RecyclerView.Adapter<homeCategoryAdapter.ViewHolder> {
    List<Category> list = List.of();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainListItemBinding binding = MainListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(List<Category> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        private final MainListItemBinding binding;

        public ViewHolder(MainListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category item) {
            binding.name.setText(item.getName());
            binding.icon.setText(item.getImageIcon());
        }
    }
}
