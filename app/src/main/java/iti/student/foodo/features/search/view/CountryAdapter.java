package iti.student.foodo.features.search.view;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import iti.student.foodo.data.model.domain.Category;
import iti.student.foodo.data.model.domain.Country;
import iti.student.foodo.databinding.FilterListItemBinding;
import iti.student.foodo.features.home.view.OnFilterItemClick;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    List<Country> list = List.of();

    private final OnFilterItemClick filterItemClick;
    private int selectedItem = -1;

    public CountryAdapter(OnFilterItemClick filterItemClick) {
        this.filterItemClick = filterItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterListItemBinding binding = FilterListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(List<Country> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        private final FilterListItemBinding binding;

        public ViewHolder(FilterListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Country item, int position) {
            binding.name.setText(item.getName());
            Glide.with(binding.getRoot()).load(item.getImage())
                    .thumbnail(0.01f)
                    .into(binding.image);
            if (selectedItem == position) {
                binding.selectedItem.setVisibility(VISIBLE);
            } else {
                binding.selectedItem.setVisibility(GONE);
            }
            binding.getRoot().setOnClickListener(v -> {
                selectedItem = position;
                filterItemClick.onItemClick(item.getName());
                notifyDataSetChanged();
            });
        }
    }
}
